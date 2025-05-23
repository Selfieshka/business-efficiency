package com.technokratos.kirillakhmetov.dto.response;

import java.util.List;

public record ExpenseResponse(List<String> categories, List<String> amounts) {
}
