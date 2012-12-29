// Helper functions
function getShortDate(date) {
    return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
}

function lessonsToEvents(data) {
    var events = new Array();

    $.each(data, function(index, value) {
        var lesson = value.lesson;
        var studentId = lesson.student.replace("/student/", "");
        var instructorId = lesson.instructor.replace("/instructor/", "");
        events.push({id: lesson.id, start_date: lesson.start, end_date: lesson.end, text: lesson.note, instructor_id: instructorId, student_id: studentId});
    });

    return events;
}

function instructorsToSections(data) {
    var sections = new Array();

    $.each(data, function(index, value) {
        var instructor = value.instructor;
        sections.push({key: instructor.id, label: instructor.name});
    });

    return sections;
}

function eventToLesson(event, scheduler) {
    var convert = scheduler.date.date_to_str("%Y-%m-%d %H:%i");
    var lesson = new Object();

    lesson.start = convert(event.start_date);
    lesson.end = convert(event.end_date);
    lesson.note = event.text;
    lesson.instructor = "/instructor/" + event.instructor_id;
    // TODO
    lesson.student = "/student/224";

    return {lesson: lesson};
}
