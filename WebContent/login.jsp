<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="com.jerome.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
String NID = Util.getUUID();
%>
<title>Insert title here</title>
 <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
    <script type="text/javascript" src="http://jquery-json.googlecode.com/files/jquery.json-2.2.min.js"></script>
    <script type="text/javascript">
        jQuery(function($) {

            function processEvents(events) {
                /*
                if (events.length) {
                    $('#logs').append('<span style="color: blue;">[client] ' + events.length + ' events</span><br/>');
                } else {
                    $('#logs').append('<span style="color: red;">[client] no event</span><br/>');
                }
				*/
                for (var i in events) {
                    if (events[i] !="")
                        {
                    		$('#logs').append('<span>[event] ' + events[i] + '</span><br/>');
                        }
                	
                    if ( events[i] == "pass")
                        {
                         return false;
                        }
                }
                return true;
            }

            function long_polling() {
                $.getJSON('ajax?id=<%=NID%>', function(events) {
                    if (processEvents(events))
                    {
                    	long_polling();
                    }
                    else
                    {
                    	window.location.href="./suc.html";
                    }
                });
            }

            long_polling();

        });
    </script>
</head>
<body>
<h1><%="Wait for auth: " + NID%></h1>
<%Util.startWaiting(NID); %>
<div id="logs" style="font-family: monospace;">
</body>
</html>