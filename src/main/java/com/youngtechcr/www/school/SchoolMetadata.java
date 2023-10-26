package com.youngtechcr.www.school;

import com.youngtechcr.www.school.subject.SchoolSubject;

import java.util.List;

public record SchoolMetadata(
    List<SchoolSubject> technicalSubjects,
    List<SchoolSubject> academicSubjects
) {
}
