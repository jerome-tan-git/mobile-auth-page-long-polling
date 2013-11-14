<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>HTTP Polling</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
    <script type="text/javascript" src="http://jquery-json.googlecode.com/files/jquery.json-2.2.min.js"></script>
    <script type="text/javascript">
        jQuery(function($) {

            function processEvents(events) {
                if (events.length) {
                    $('#logs').append('<span style="color: blue;">[client] ' + events.length + ' events</span><br/>');
                } else {
                    $('#logs').append('<span style="color: red;">[client] no event</span><br/>');
                }
                for (var i in events) {
                    $('#logs').append('<span>[event] ' + events[i] + '</span><br/>');
                }
            }

            function long_polling() {
                $.getJSON('ajax?name=a&', function(events) {
                    processEvents(events);
                    long_polling();
                });
            }

            long_polling();

        });
    </script>
</head>
<body>
<div id="logs" style="font-family: monospace;">
</div>
</body>
</html>
