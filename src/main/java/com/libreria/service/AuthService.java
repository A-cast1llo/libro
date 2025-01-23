package com.libreria.service;

import com.libreria.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}