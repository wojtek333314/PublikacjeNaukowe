<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body{
            overflow-x:hidden;
            width: 100%;
            background-image: url("../resources/img/background_basic.jpg");
        }

        .form-horizontal{
            margin-top: 20%;
        }
    </style>
</head>
<body>

<form action="/rest/api/login" method="post" class="form-horizontal">
    <fieldset class="form-group">
        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="logininput">Login</label>
            <div class="col-md-4">
                <input id="logininput" name="logininput" type="text" placeholder="Enter login" class="form-control input-md" required="">
            </div>
        </div>

        <!-- Password input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="passwordinput">Password</label>
            <div class="col-md-4">
                <input id="passwordinput" name="passwordinput" type="password" placeholder="Enter password" class="form-control input-md" required="">

            </div>
        </div>

        <!-- Button -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="loginbutton"></label>
            <div class="col-md-4" >
                <button type="submit" id="loginbutton" name="loginbutton" class="btn btn-primary btn-block center-block">Login</button>
            </div>
        </div>

    </fieldset>
</form>
<script type="text/javascript" src="../resources/js/bootstrap.min.js"></script>
</body>
</html>