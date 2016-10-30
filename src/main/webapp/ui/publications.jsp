<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Publications</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link href="../resources/css/bootstrap.css" rel="stylesheet">

    <style>
        body {
            overflow-x: hidden;
            width: 100%;
            background-image: url("../resources/img/background_basic.jpg");
        }

        .container {
            margin-top: 5%;
            background-color: #4e4e4e;
            color: #ffffff;
        }

        tr:hover {
            background-color: #626262;
        }


    </style>
</head>
<body>
<div class="container ">
    <table class="table table-sm">
        <thead>
        <tr>
            <th >#</th>
            <th class="col-xs-5">Title</th>
            <th class="col-xs-8">Author</th>
            <th class="col-xs-2"></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row">1</th>
            <td>Mark</td>
            <td>Otto</td>
            <td>
                <button class="btn btn-primary btn-block center-block">See more</button>
            </td>
        </tr>
        <tr>
            <th scope="row">2</th>
            <td>${message}</td>
            <td>Thornton</td>
            <td>
                <button class="btn btn-primary center-block">See more</button>
            </td>
        </tr>
        <tr>
            <th scope="row">3</th>
            <td>Larry the Bird</td>
            <td>Thornton</td>
            <td>
                <button class="btn btn-primary center-block">See more</button>
            </td>
        </tr>
        </tbody>
    </table>
</div>


<script type="text/javascript" src="../resources/js/bootstrap.js"></script>
</body>
</html>