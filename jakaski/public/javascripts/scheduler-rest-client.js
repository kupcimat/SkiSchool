// Helper functions
function zero(number) {
    var prefix = "";
    if (number < 10) {
        prefix = "0";
    }

    return prefix + number;
}

function getShortDate(date) {
    return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
}

function getShortTime(start, end) {
    return zero(start.getHours()) + ":" + zero(start.getMinutes()) + " - " + zero(end.getHours()) + ":" + zero(end.getMinutes());
}

function html(id) {
    return document.getElementById(id);
}

function generateTimeValues(time) {
    var offset = "00";
    var result = new Array();
    var pattern = new RegExp("[0-1][0-8]:[0-5][0-9]");

    if (time != null && pattern.test(time)) {
        offset = time.substr(3, 2);
    }

    for (var hour = 8; hour <= 18; hour++) {
        result.push(zero(hour) + ":" + offset);
    }

    return result;
}

// REST API objects to scheduler objects
function availabilitiesToEvents(data) {
    var events = new Array();

    $.each(data, function(index, value) {
        var availability = value.availability;
        var instructorId = availability.instructor.replace("/instructor/", "");
        events.push({
            id:            availability.id,
            start_date:    availability.start,
            end_date:      availability.end,
            text:          availability.note,
            instructor_id: instructorId,
            type:          "availability"});
    });

    return events;
}

function lessonsToEvents(data) {
    var events = new Array();

    $.each(data, function(index, value) {
        var lesson = value.lesson;
        var studentId = lesson.student.replace("/student/", "");
        var instructorId = lesson.instructor.replace("/instructor/", "");
        events.push({
            id:            lesson.id,
            start_date:    lesson.start,
            end_date:      lesson.end,
            text:          lesson.note,
            instructor_id: instructorId,
            student_id:    studentId,
            location:      lesson.location,
            language:      lesson.language,
            snowboard:     lesson.snowboard,
            type:          lesson.type,
            count:         lesson.count,
            student_name:  lesson.studentName});
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

function instructorsToMap(data) {
    var map = new Object();
    var names = new Array();
    var sections = instructorsToSections(data);

    $.each(sections, function(index, value) {
        map[value.label] = value;
        names.push(value.label);
    });

    return {map: map, names: names};
}

function studentsToMap(data) {
    var map = new Object();
    var names = new Array();

    $.each(data, function(index, value) {
        var student = value.student;
        map[student.name] = student;
        names.push(student.name);
    });

    return {map: map, names: names};
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
    lesson.student = "/student/" + event.student_id;
    lesson.location = event.location;
    lesson.language = event.language;
    lesson.snowboard = event.snowboard;
    lesson.type = event.type;
    lesson.count = event.count;

    return {lesson: lesson};
}

// Data loading in scheduler
function loadLessons(date, location, scheduler) {
    $.ajax({
        type: "GET",
        url: "/query/lessons" + (location == "null" ? "" : "/" + location),
        data: {"date": getShortDate(date)}
    }).done(function(data) {
        scheduler.parse(lessonsToEvents(data.result), "json");
    });
}

function loadInstructorLessons(instructorId, startDate, endDate, scheduler) {
    $.ajax({
        type: "GET",
        url: "/query/lessons/instructor/" + instructorId,
        data: {"start": getShortDate(startDate), "end": getShortDate(endDate)}
    }).done(function(data) {
        scheduler.parse(lessonsToEvents(data.result), "json");
    });
}

function loadAvailabilities(date, location, scheduler) {
    $.ajax({
        type: "GET",
        url: "/query/availabilities"  + (location == "null" ? "" : "/" + location),
        data: {"date": getShortDate(date)}
    }).done(function(data) {
        scheduler.parse(availabilitiesToEvents(data.result), "json");
    });
}

function loadInstructorAvailabilities(instructorId, startDate, endDate, scheduler) {
    $.ajax({
        type: "GET",
        url: "/query/availabilities/instructor/" + instructorId,
        data: {"start": getShortDate(startDate), "end": getShortDate(endDate)}
    }).done(function(data) {
        scheduler.parse(availabilitiesToEvents(data.result), "json");
    });
}

function loadSections(date, location, scheduler) {
    $.ajax({
        type: "GET",
        url: "/query/instructors" + (location == "null" ? "" : "/" + location),
        data: {"date": getShortDate(date)}
    }).done(function(data) {
        $.each(instructorsToSections(data.result), function(index, value) {
            if (scheduler.getSection(value.key) == null) {
                scheduler.addSection(value, null);
            }
        });
    });
}
