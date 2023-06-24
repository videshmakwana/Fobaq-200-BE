package com.brilworks.accounts.role_permissions.enums;

import lombok.Getter;

@Getter
public enum PermissionsEnum {
    ALL_OKR,
    ALL_TASK,
    ALL_EMP,
    ALL,
    // crm
    ALL_EMP_READ,
    ALL_EMP_WRITE,
    SUBORDINATE_EMP_READ,
    SUBORDINATE_EMP_WRITE,
    ALL_LEAVE_READ,
    ALL_LEAVE_WRITE,
    SUBORDINATE_LEAVE_READ,
    SUBORDINATE_LEAVE_WRITE,
    LEAVE_SETTINGS_READ,
    LEAVE_SETTINGS_WRITE,
    FILE_READ,
    FILE_WRITE,
    ANNOUNCEMENT_READ,
    ANNOUNCEMENT_WRITE,
    BASE_EMP_PERMISSION,
    //task
    ALL_PROJECT_READ,
    ALL_PROJECT_WRITE,
    NEW_PROJECT_WRITE,
    MY_RESOURCE_ALLOCATION_READ,
    MY_RESOURCE_ALLOCATION_WRITE,
    ALL_RESOURCE_ALLOCATION_READ,
    ALL_RESOURCE_ALLOCATION_WRITE,
    BASE_TASK_PERMISSION,
    //okr
    MY_OKR_READ,
    MY_OKR_WRITE,
    BASE_OKR_PERMISSION,
}
