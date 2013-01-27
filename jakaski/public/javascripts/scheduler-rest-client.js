// Helper functions
function getShortDate(date) {
    return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
}

function html(id) {
    return document.getElementById(id);
}

// REST API objects to scheduler objects
function availabilitiesToEvents(data) {
    var events = new Array();

    $.each(data, function(index, value) {
        var availability = value.availability;
        var instructorId = availability.instructor.replace("/instructor/", "");
        events.push({id: availability.id, start_date: availability.start, end_date: availability.end, text: availability.note, instructor_id: instructorId, type: "availability"});
    });

    return events;
}

function lessonsToEvents(data) {
    var events = new Array();

    $.each(data, function(index, value) {
        var lesson = value.lesson;
        var studentId = lesson.student.replace("/student/", "");
        var instructorId = lesson.instructor.replace("/instructor/", "");
        events.push({id: lesson.id, start_date: lesson.start, end_date: lesson.end, text: lesson.note, instructor_id: instructorId, student_id: studentId, type: "lesson"});
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

function studentsToList(data) {
    var list = new Array();

    $.each(data, function(index, value) {
        var student = value.student;
        list.push(student.name);
    });

    return list;
}

// Scheduler objects to REST API objects
function eventToAvailability(event, scheduler) {
    var convert = scheduler.date.date_to_str("%Y-%m-%d %H:%i");
    var availability = new Object();

    availability.start = convert(event.start_date);
    availability.end = convert(event.end_date);
    availability.note = event.text;
    availability.instructor = "/instructor/" + event.instructor_id;

    return {availability: availability};
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

// Data loading in scheduler
function loadLessons(date, scheduler) {
    $.ajax({
        type: "GET",
        url: "/query/lessons",
        data: "date=" + getShortDate(date)
    }).done(function(data) {
        scheduler.parse(lessonsToEvents(data.result), "json");
    });
}

function loadAvailabilities(date, scheduler) {
    $.ajax({
        type: "GET",
        url: "/query/availabilities",
        data: "date=" + getShortDate(date)
    }).done(function(data) {
        scheduler.parse(availabilitiesToEvents(data.result), "json");
    });
}

function loadSections(date, scheduler) {
    $.ajax({
        type: "GET",
        url: "/query/instructors",
        data: "date=" + getShortDate(date)
    }).done(function(data) {
        $.each(instructorsToSections(data.result), function(index, value) {
            if (scheduler.getSection(value.key) == null) {
                scheduler.addSection(value, null);
            }
        });
    });
}
