package com.youngtechcr.www.school;

import com.youngtechcr.www.school.group.SchoolGroupsMetadata;
import com.youngtechcr.www.school.subject.SchoolSubject;

import java.util.List;

public record SchoolMetadata(
    int schoolId,
    List<SchoolSubject> technicalSubjects,
    List<SchoolSubject> academicSubjects,
    List<SchoolGroupsMetadata> groups
) {
}
