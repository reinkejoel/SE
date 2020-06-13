<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Parkhaus</title>
<script src="https://ccmjs.github.io/mkaul-components/parkhaus/versions/ccm.parkhaus-7.0.0.js""></script>
</head>
<body>
<h1>Parkhaus</h1>
<ccm-parkhaus-7-0-0
server_url="http://localhost:8081/ParkServ/Parkhaus"
extra_buttons='["Summe","Average","Ausgeparkt"]'
extra_charts='["chart","langzeit"]'
></ccm-parkhaus-7-0-0>
</body>
</html>