package test02.dto;

import test02.model.User;

public record UserDto(Long id, String name, String surname, double balance, int numerOfTransfers) {

    public static UserDto from (User user) {
        return new UserDto(user.getId(), user.getName(), user.getSurname(), user.getBalance(), user.getTransactionCounter());
    }
}
