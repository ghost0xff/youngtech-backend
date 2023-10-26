package com.youngtechcr.www.school;

public record UserSchoolMetadata(
        boolean isMember,
        SchoolMemberType type,
        int schoolId
) { }
