package com.brilworks.accounts.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecStatus {
    INVITED,
    ACTIVE,
    CREATED,DELETED
}
