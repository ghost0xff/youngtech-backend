package com.youngtechcr.www.school.group;

import com.youngtechcr.www.school.subject.SchoolSubjectMetadata;

public record SchoolGroupsMetadata(
        int id, String name, SchoolSubjectMetadata technicalSubject
) {
}
