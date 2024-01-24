package com.common.nayong.constants;

import com.common.nayong.permission.LicenseRightAnnotation;
import com.common.nayong.permission.RightType;

public class PermissionConstants {
    @LicenseRightAnnotation(displayKey = "fullAccessPermission", type = RightType.SuperUser)
    public final static String FULL_ACCESS_PERMISSION = "full_access_permission_superuser";
    @LicenseRightAnnotation(displayKey = "editUser", type = RightType.Normal)
    public final static String EDIT_USER = "edit_user";
}
