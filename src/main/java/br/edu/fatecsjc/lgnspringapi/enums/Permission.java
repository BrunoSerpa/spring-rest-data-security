package br.edu.fatecsjc.lgnspringapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    ADMIN_CREATE("PERMISSION_ADMIN_CREATE"),
    ADMIN_UPDATE("PERMISSION_ADMIN_UPDATE");

    private final String permissionValue;
}