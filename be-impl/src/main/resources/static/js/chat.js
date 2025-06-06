let stompClient = null;
let username = document.getElementById('username').value;
const currentPath = window.location.pathname;
const pathParts = currentPath.split('/');
const basePath = '/' + pathParts[1];

function connect() {
    if (!username) {
        console.error('Username не найден');
        return;
    }

    let socket = new SockJS(basePath + '/ws-chat');
    stompClient = Stomp.over(socket);

    stompClient.debug = null;

    socket.onopen = function () {
        console.log('Соединение установлено');
    };

    socket.onclose = function () {
        console.log('Соединение закрыто');
    };

    socket.onerror = function (error) {
        console.error('Ошибка:', error);
    };

    stompClient.connect({},
        function (frame) {
            console.log('STOMP соединение установлено:', frame);
            onConnected();
        },
        function (error) {
            console.error('STOMP ошибка подключения:', error);
            onError(error);
        }
    );
}

function onConnected() {
    stompClient.subscribe('/topic/public',
        function (message) {
            onMessageReceived(message);
        },
        function (error) {
            console.error('Ошибка подписки:', error);
        }
    );

    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    );
}

function onError(error) {
    console.error('Ошибка WebSocket:', error);
}

function sendMessage() {
    let messageContent = document.getElementById('message').value.trim();
    if (messageContent && stompClient) {
        let chatMessage = {
            sender: username,
            content: messageContent,
            type: 'CHAT',
            timestamp: new Date().toISOString()
        };

        addMessageToChat(chatMessage);

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        document.getElementById('message').value = '';
    }
}

function addMessageToChat(message) {
    let messageElement = document.createElement('div');
    messageElement.classList.add('message');

    if (message.type === 'JOIN') {
        messageElement.classList.add('received');
        messageElement.innerHTML = `
            <div class="sender">Система</div>
            <div class="content">${message.sender} присоединился к чату!</div>
            <div class="time">${new Date().toLocaleTimeString()}</div>
        `;
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('received');
        messageElement.innerHTML = `
            <div class="sender">Система</div>
            <div class="content">${message.sender} покинул чат!</div>
            <div class="time">${new Date().toLocaleTimeString()}</div>
        `;
    } else {
        messageElement.classList.add(message.sender === username ? 'sent' : 'received');
        messageElement.innerHTML = `
            <div class="sender">${message.sender}</div>
            <div class="content">${message.content}</div>
            <div class="time">${new Date(message.timestamp).toLocaleTimeString()}</div>
        `;
    }

    document.getElementById('chat-messages').appendChild(messageElement);
    document.getElementById('chat-messages').scrollTop = document.getElementById('chat-messages').scrollHeight;
}

function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);
    if (message.type === 'CHAT' && message.sender === username) {
        return;
    }
    addMessageToChat(message);
}

connect();

document.getElementById('message').addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
        sendMessage();
    }
});