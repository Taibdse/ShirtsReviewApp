<%-- 
    Document   : crawl
    Created on : Dec 2, 2019, 10:00:51 AM
    Author     : HOME
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="static/css/style.css">
    <title>Crawl</title>
</head>
<body style="background-color: lightblue">
    <h2 class="text-center mt-5">Crawler</h2>
    <div class="container">
        <div class="row mt-3">
            <div class="col-sm-6 px-5 text-center mx-auto">
                <form action="CrawlerServlet" method="POST">
                    <input name="btAction" type="submit" value="Crawl routine.vn website" class="btn btn-success"><br/>
                </form>
                <form action="CrawlerServlet" method="POST">
                    <input name="btAction" class="btn btn-warning mt-3" type="submit" value="Crawl kapo.vn website">
                </form>
            </div>
        </div>
    </div>
    <script src=""></script>
</body>
</html>

