//calendar
$(document).ready(function() {
  $('#calendar').fullCalendar({
    locale: 'es',
    header: {
      left: 'prev,next today',
      center: 'title',
      right: 'month,agendaWeek,agendaDay'
    },
    defaultDate: '2017-11-12',
    weekNumbers: true,
          navLinks: true, // can click day/week names to navigate views
          startEditable: true,
          editable: true,
          eventLimit: true, // allow "more" link when too many events
          theme: true,
          events: [
          {
            title: 'Satrting',
            start: '2017-11-01T07:30:00',
          },
          {
            title: 'WorkOut',
            start: '2017-11-01T08:00'
          },          {
            title: 'Satrting',
            start: '2017-11-01T14:30'
          },
          {
            title: 'WorkOut',
            start: '2017-11-01T15:00'
          },
          {
            title: 'Satrting',
            start: '2017-11-02T07:30'
          },
          {
            title: 'WorkOut',
            start: '2017-11-02T08:00'
          },
          {
            title: 'Satrting',
            start: '2017-11-03T07:30'
          },
          {
            title: 'WorkOut',
            start: '2017-11-03T08:00'
          },
          {
            title: 'Satrting',
            start: '2017-11-04T07:30'
          },
          {
            title: 'WorkOut',
            start: '2017-11-04T08:00'
          },
          {
            title: 'Satrting',
            start: '2017-11-06T07:30'
          },
          {
            title: 'WorkOut',
            start: '2017-11-06T08:00'
          },
          {
            title: 'Satrting',
            start: '2017-11-07T07:30'
          },
          {
            title: 'WorkOut',
            start: '2017-11-07T08:00'
          },
          {
            title: 'Satrting',
            start: '2017-11-08T07:30'
          },
          {
            title: 'WorkOut',
            start: '2017-11-08T08:00'
          },
          {
            title: 'Satrting',
            start: '2017-11-09T07:30'
          },
          {
            title: 'WorkOut',
            start: '2017-11-09T08:00'
          },
          {
            title: 'Satrting',
            start: '2017-11-10T07:30'
          },
          {
            title: 'WorkOut',
            start: '2017-11-10T08:00'
          },
          {
            title: 'Satrting',
            start: '2017-11-11T07:30'
          },
          {
            title: 'WorkOut',
            start: '2017-11-11T08:00'
          },
          {
            title: 'Satrting',
            start: '2017-11-13T07:30'
          },
          {
            title: 'WorkOut',
            start: '2017-11-13T08:00'
          },
          {
            title: 'Satrting',
            start: '2017-11-14T07:30'
          },
          {
            title: 'WorkOut-Salitre',
            start: '2017-11-14T08:00',
          },
          {
          }
          ]
        })
});