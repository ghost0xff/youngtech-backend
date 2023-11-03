package com.youngtechcr.www.school.registration;

import com.youngtechcr.www.school.SchoolMemberType;

public record SchoolRegistration(
        int schoolId,
        int groupId,
        int subjectId,
        String comment
){ }