// JavaScript and JQuery - NewtonJoshua.com



// Ajax Switch tab

function ajax(tab) {

    var xmlhttp;
    if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else { // code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            document.getElementById("contents").innerHTML = xmlhttp.responseText;
            document.getElementById("home").style.display = "none";
            document.getElementById("contents").style.display = "";
        }
    }
    xmlhttp.open("GET", "/" + tab + ".html", true);
    xmlhttp.send();
}

//Active the seleted tab

function active(tab) {
    document.getElementById('AboutMe').className = "";
    document.getElementById('Resume').className = "";
    document.getElementById('Developer').className = "";
    document.getElementById(tab).className += "active";
}
// Send mail on 1st login for the day

function SendMail(user, name, mail, type) {
    var d = new Date();
    var sendMail = false;
    if (type == 'FB') {
        if (localStorage.FBmail !== mail || localStorage.date !== d.toDateString()) {
            sendMail = true;
        }
        localStorage.FBmail = mail;
        document.getElementById("AITAMmail").value = localStorage.FBmail;
        document.getElementById("AITAMname").value = localStorage.FBname;
        localStorage.date = d.toDateString();
    }
    if (type == 'G') {
        if (localStorage.Gmail !== mail || localStorage.date !== d.toDateString()) {
            sendMail = true;
        }
        localStorage.Gmail = mail;
        document.getElementById("AITAMmail").value = localStorage.Gmail;
        document.getElementById("AITAMname").value = localStorage.Gname;
        localStorage.date = d.toDateString();
    }
    if (sendMail) {
        var xmlhttp;
        if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        } else { // code for IE6, IE5
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                if (JSON.parse(xmlhttp.responseText).Status == "Success") {
                    var msgTopic = "Dear " + name;
                    var msgOptions = {
                        body: "Greetings from Newton Joshua. Welcome to my website. Cheers!!! ",
                        icon: "images/nn.jpg"
                    };
                    if (!("Notification" in window)) {
                        console.log("This browser does not support desktop notification");
                    } else if (Notification.permission === "granted") {
                        var notification = new Notification(msgTopic, msgOptions);
                    } else if (Notification.permission !== 'denied') {
                        Notification.requestPermission(function (permission) {
                            if (permission === "granted") {
                                var notification = new Notification(msgTopic, msgOptions);
                            }
                        });
                    }
                }
            }
        }
        xmlhttp.open("POST", "/Controller?action=Login&name=" + name + "&user=" + user, true);
        xmlhttp.send();
    }

}

//Print Modal


function PrintElem(elem) {
    Popup($('#' + elem).html(), elem);
}

function Popup(data, elem) {
    var mywindow = window.open('', elem, 'height=400,width=600');
    mywindow.document.write('<html><head><title>' + elem + '</title>');
    mywindow.document.write('<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />');
    mywindow.document.write('</head><body >');
    mywindow.document.write(data);
    mywindow.document.write('</body></html>');

    mywindow.document.close(); // necessary for IE >= 10
    mywindow.focus(); // necessary for IE >= 10

    mywindow.print();
    mywindow.close();

    return true;
}

// FB Login
// This is called with the results from from FB.getLoginStatus().
function statusChangeCallback(response) {
    // The response object is returned with a status field that lets the
    // app know the current login status of the person.
    // Full docs on the response object can be found in the documentation
    // for FB.getLoginStatus().
    if (response.status === 'connected') {
        // Logged into your app and Facebook.
        testAPI();
    } else if (response.status === 'not_authorized') {
        // The person is logged into Facebook, but not your app.
        document.getElementById('status').innerHTML = 'Please log ' +
            'into this app.';
    } else {
        // The person is not logged into Facebook, so we're not sure if
        // they are logged into this app or not.
        document.getElementById('status').innerHTML = 'Please log ' +
            'into Facebook.';
    }
}

// This function is called when someone finishes with the Login
// Button.  See the onlogin handler attached to it in the sample
// code below.
function checkLoginState() {
    FB.getLoginStatus(function (response) {
        statusChangeCallback(response);
    });
}

window.fbAsyncInit = function () {
    FB.init({
        appId: '771830059601215',
        cookie: true, // enable cookies to allow the server to access 
        // the session
        xfbml: true, // parse social plugins on this page
        version: 'v2.3' // use version 2.2
    });

    // Now that we've initialized the JavaScript SDK, we call 
    // FB.getLoginStatus().  This function gets the state of the
    // person visiting this page and can return one of three states to
    // the callback you provide.  They can be:
    //
    // 1. Logged into your app ('connected')
    // 2. Logged into Facebook, but not your app ('not_authorized')
    // 3. Not logged into Facebook and can't tell if they are logged into
    //    your app or not.
    //
    // These three cases are handled in the callback function.

    FB.getLoginStatus(function (response) {
        statusChangeCallback(response);
    });

};

// Load the SDK asynchronously
(function (d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s);
    js.id = id;
    js.src = "js/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

// Here we run a very simple test of the Graph API after login is
// successful.  See statusChangeCallback() for when this call is made.


function testAPI() {
    FB.api('/me', function (response) {
        localStorage.FBname = response.name;
        localStorage.FBemail = response.email;

        FB.api(
            "/" + response.id + "/picture",
            function (ImgResponse) {
                if (ImgResponse && !ImgResponse.error) {
                    /* handle the result */
                    localStorage.FBimage = (ImgResponse.data.url).replace("https", "http");
                }
            }
        );
        document.getElementById("continue").disabled = false;
        //localStorage.logIn=1;
        document.getElementById('fUser').innerHTML =
            '<img src=' + localStorage.FBimage + ' class="img-circle"  width="50px"><b>' + localStorage.FBname + '</b><br><p><small>' + localStorage.FBemail + '</small></p>';
        document.getElementById('userDetails').innerHTML =
            '<table><tr><td><img src=' + localStorage.FBimage + ' class="img-circle"  width="50px"></td><td><b>' + localStorage.FBname + '</b><br><p><small>' + localStorage.FBemail + '</small></p></td></tr></table>';
        //        SendMail('<table><tr><td><a href="https://facebook.com/'+response.id+'"><img src="https://upload.wikimedia.org/wikipedia/commons/8/82/Facebook_icon.jpg" class="img-circle"  width="50px"></a></td><td><b>'+localStorage.FBname+'</b><br><p><small>'+localStorage.FBemail+'</small></p></td></tr></table>',localStorage.FBname);
        SendMail('<table><tr><td></td><td><b>' + localStorage.FBname + '</b><br><p><small>' + localStorage.FBemail + '</small></p><p><small>https://facebook.com/' + response.id + '</small></p></td></tr></table>', localStorage.FBname, localStorage.FBemail, 'FB');
    });
}

// GitHub Feeds

function GitFeed() {
    var xmlhttp;
    if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else { // code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

            var res = JSON.parse(xmlhttp.responseText);
            var arr = res.GitHub;
            var GitHub = document.getElementById('GitHub');
            $("#Gitload").empty();
            for (var i = 0; i < arr.length; i++) {
                var GitFeed = document.createElement('a');
                GitFeed.href = arr[i].Link;
                GitFeed.className = "list-group-item";
                GitFeed.innerHTML = "<div data-toggle='tooltip' data-placement='bottom' title='" + arr[i].Title + "'>" + arr[i].Activity + " " + arr[i].Repository + "<br>" + arr[i].Date + "</div>";
                GitHub.appendChild(GitFeed);
            }
            GitHub.style.height = '400px';
            GitHub.style.overflow = 'scroll';
            $('[data-toggle="tooltip"]').tooltip();
            FaceBook.style.height = '400px';
            FaceBook.style.overflow = 'scroll';
        }

    }

    xmlhttp.open("POST", "/Controller?action=GitHub", true);
    xmlhttp.send();
}


// FaceBook Posts

function FbFeed() {

    //                var PostsAccessToken="CAAK9ZBbCHjT8BAGStUZCr1BKxOzKZCEitmzgIDtvM6pq0vx1136lAo5yKzBqduDbuwsZAvskYtdy8ikE19SRufmpJdDC1KaVyx29PCUQ1ZBGPTM6VoKHiDpK57UtObvi5EFd1QEbaBtT1koEf5O5KLd4wmJrrufZATEmpscTz5Ib5jRMXuNGPLFJJ4wemhD8HLhIY0yf0bZBDuP5ePl6tFs";
    //            FB.api(
    //            "/1008531222504381/feed",{access_token : PostsAccessToken},
    //            function (response) {
    //              if (response ) {
    ////                console.log(JSON.stringify(response));
    //                  var res= JSON.parse(JSON.stringify(response));
    //                  var data= res.data;
    //                  for (var i=0; i< data.length; i++){
    //                      var ele= JSON.parse(JSON.stringify(data[i]));
    //                      if(JSON.parse(JSON.stringify(ele.from)).id == "1008531222504381"){
    //                           console.log(ele.from);
    //                          console.log(ele.story);
    //                          console.log(ele.message);
    //                          console.log(ele.created_time);
    //                          console.log(ele.picture);
    //                          console.log(JSON.parse(JSON.stringify(ele.actions[0])).link);
    //
    //                      }
    //                     
    //                  }
    //                  
    //              }
    //            }
    //        ); 
    var xmlhttp;
    if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else { // code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

            var res = JSON.parse(xmlhttp.responseText);
            var arr = res.Facebook;
            var FaceBook = document.getElementById('FaceBook');
            $("#FBload").empty();
            for (var i = 0; i < arr.length; i++) {
                var FBPost = document.createElement('a');
                FBPost.href = arr[i].link;
                FBPost.className = "list-group-item";
                FBPost.innerHTML = "<div class='row' data-toggle='tooltip' data-placement='bottom' title='" + arr[i].message + "'><div class='col-md-3'><img src='" + arr[i].picture + "' width='100%'></div><div class='col-md-9'>" + arr[i].story + "<br>" + arr[i].date + "</div></div>";
                $(FBPost).tooltip();
                FaceBook.appendChild(FBPost);
            }
            $('[data-toggle="tooltip"]').tooltip();
            FaceBook.style.height = '400px';
            FaceBook.style.overflow = 'scroll';
        }

    }

    xmlhttp.open("POST", "/Controller?action=FaceBook", true);
    xmlhttp.send();

}

// Get Feeds

function feeds() {
    FbFeed();
    GitFeed();
    document.getElementById("feedBody").style.display = "";
}

// Reload Page

function reload() {
    document.getElementById("home").style.display = "";
    document.getElementById("contents").style.display = "none";
}

// Google Sign In

function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    localStorage.Gemail = profile.getEmail();
    localStorage.Gname = profile.getName();
    if (profile.getImageUrl()) {
        localStorage.Gimage = profile.getImageUrl().replace("https", "http");
    }
    if (!(profile.getImageUrl())) {
        localStorage.Gimage = "images/google.png";
    }
    document.getElementById("continue").disabled = false;
    // localStorage.logIn=1;
    document.getElementById('gUser').innerHTML =
        '<img src=' + localStorage.Gimage + ' class="img-circle"  width="50px"><b>' + localStorage.Gname + '</b><br><p><small>' + localStorage.Gemail + '</small></p>';

    document.getElementById('userDetails').innerHTML =
        '<table><tr><td><img src=' + localStorage.Gimage + ' class="img-circle"  width="50px"></td><td><b>' + localStorage.Gname + '</b><br><p><small>' + localStorage.Gemail + '</small></p></td></tr></table>';
    SendMail('<table><tr><td><img src=' + localStorage.Gimage + ' class="img-circle"  width="50px"></td><td><b>' + localStorage.Gname + '</b><br><p><small>' + localStorage.Gemail + '</small></p></td></tr></table>', localStorage.Gname, localStorage.Gemail, 'G');

};

// On Login

function login() {
    localStorage.logIn = 1;
}

// Modal Actions

$('#myLogin').on('hidden.bs.modal', function () {

    if (localStorage.logIn == 0) {
        if (localStorage.logedIn != 1) {
            localStorage.logedIn = 0;
            window.location.href = "https://www.facebook.com/NewtonJoshua.A";
        }
    } else {
        loadPics();
        feeds();
        GAReport();
    }
})

$('#privacy, #terms, #Disclaimer').on('shown.bs.modal', function () {

    if (localStorage.logIn == 0) {
        $('#myLogin').modal('hide');
        localStorage.logedIn = 1;
    }
})

$('#privacy, #terms, #Disclaimer').on('hidden.bs.modal', function () {

    if (localStorage.logIn == 0) {
        $('#myLogin').modal('show');
    }
})

// Load Family Pics and Apply AirView

function loadPics() {
    var FamilYPics = document.getElementById('FamilYPics');
    $(FamilYPics).empty();
    for (var i = 1; i < 81; i++) {
        var Pic = document.createElement('img');
        Pic.src = "Pics/thumbnails/" + i + ".jpg";
        Pic.className = "img-thumbnail";
        Pic.height = "50";
        Pic.title = i + ".jpg";
        $(Pic).airview({
            url: './Pics/',
            width: 300,
            container: 'body'
        });
        FamilYPics.appendChild(Pic);
    }
}

// Apply Airview to myFavorites

$("fav").airview({
    url: './Pics/Fav/',
    width: 200,
    container: 'body'
});

$("potc").airview({
    url: './Pics/Fav/',
    width: 500,
    container: 'body'
});

// Google Analytics Report

//Read Only GA refresh token
var refreshToken = "1/zKrnUuw8m9jlpSPwAMy81kfCelHOT0iyGCKvasFLXs5IgOrJDtdun6zK6XiATCKT";
google.load("visualization", "1", {
    packages: ["geochart", "corechart"]
});

function GAReport() {

    if (localStorage.access_token == null) {
        console.log("No token");
        GAgetToken();
    }
    if (localStorage.access_token != null) {
        console.log(localStorage.access_token);
        checkValidity();
    }

}

// Check Validity of Access Token
function checkValidity() {
    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4) {
            var res = JSON.parse(xmlhttp.responseText);
            console.log(res.expires_in);
            console.log(res.hasOwnProperty("expires_in"));
            var check = false;
            check = res.hasOwnProperty("expires_in");
            if (check) {
                GAgetCountry();
                GAgetBrowser();
            }
            if (!check) {
                GAgetToken();
            }
        }
    }
    xmlhttp.open("POST", "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=" + localStorage.access_token, true);
    xmlhttp.send();
}

// Get AccessToken from Refresh Token
function GAgetToken() {
    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            var res = JSON.parse(xmlhttp.responseText);
            console.log("New Token");
            localStorage.access_token = res.access_token;
            GAgetCountry();
            GAgetBrowser();
        }
    }
    xmlhttp.open("POST", "https://www.googleapis.com/oauth2/v3/token?client_id=836282602576-qtdor9pmimt5vm9opi208nuui4tv8t8l.apps.googleusercontent.com&client_secret=H2SnX7QZK_xfEjH91Gj7dQX3&grant_type=refresh_token&refresh_token=" + refreshToken, true);
    xmlhttp.send();
}

//Get Country Data
function GAgetCountry() {
    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            var res = (JSON.parse(xmlhttp.responseText)).rows;
            // res[7]=res[0];
            var head = res[0];
            head[0] = 'Country';
            head[1] = 'Users';
            for (var i = 1; i < res.length; i++) {
                var d = res[i];
                d[1] = Number(d[1]);
            }

            drawRegionsMap(res);

        }
    }
    xmlhttp.open("GET", "https://www.googleapis.com/analytics/v3/data/ga?ids=ga%3A104796047&start-date=2015-07-01&end-date=today&metrics=ga%3Ausers&dimensions=ga%3Acountry&sort=ga%3Ausers&filters=ga%3Ausers%3E10&max-results=50&access_token=" + localStorage.access_token, true);
    xmlhttp.send();
}
//Get Browser Data
function GAgetBrowser() {
    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            var res = (JSON.parse(xmlhttp.responseText)).rows;
            var head = res[0];
            head[0] = 'Browsers';
            head[1] = 'Users';
            for (var i = 1; i < res.length; i++) {
                var d = res[i];
                d[1] = Number(d[1]);
            }

            drawChart(res);

        }
    }
    xmlhttp.open("GET", "https://www.googleapis.com/analytics/v3/data/ga?ids=ga%3A104796047&start-date=2015-07-01&end-date=today&metrics=ga%3Ausers&dimensions=ga%3Abrowser&access_token=" + localStorage.access_token, true);
    xmlhttp.send();
}
//Draw country Chart


function drawRegionsMap(data) {
    console.log(data);
    var data = google.visualization.arrayToDataTable(data);

    var options = {
        domain: 'IN',
        tooltip: {
            textStyle: {
                color: 'navy'
            },
            showColorCode: true
        },
        legend: {
            textStyle: {
                color: 'navy',
                fontSize: 12
            }
        },
        colorAxis: {
            colors: ['#00FFFF', '#0000FF']
        }
    };

    var chart = new google.visualization.GeoChart(document.getElementById('regions_div'));

    chart.draw(data, options);
}

// Draw browser chart

function drawChart(data) {

    var data = google.visualization.arrayToDataTable(data);

    var options = {
        title: 'NewtonJoshua.com is viewed from,',
        titleTextStyle: {
            color: 'navy',
            fontName: 'Times New Roman',
            fontSize: 16,
            italic: true
        },
        pieHole: 0.4,
        chartArea: {
            width: '100%'
        },
        legend: {
            position: 'top',
            maxLines: 3,
            textStyle: {
                color: 'navy',
                fontSize: 12
            }
        },
    };


    var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
    chart.draw(data, options);
}

// FingerPrint

function fingerPrint() {
    var fingerprint = (new Fingerprint().get()) + "-" + new Fingerprint({
        screen_resolution: true
    }).get();
    document.getElementById("fingerprint").innerHTML = "Your browser FingerPrint is " + fingerprint.toString();
}

//ReCaptcha- Send Credentials

function captcha() {
    document.getElementById("pleasewait").style.display = "";
    document.getElementById("aitambutton").style.display = "none";
    var mail = document.getElementById("AITAMmail").value;
    var name = document.getElementById("AITAMname").value;
    var res = grecaptcha.getResponse();
    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4) {
            if (JSON.parse(xmlhttp.responseText).Captcha == "Success") {
                document.getElementById("AITAMbody").style.display = "none";
                document.getElementById("success").style.display = "";
                window.open("Cred.html", '_blank');
                //window.location.href ="https://github.com/NewtonJoshua/AITAM/raw/master/AITAM.xls";
            } else {
                document.getElementById("warn").style.display = "";
                document.getElementById("pleasewait").style.display = "none";
                document.getElementById("aitambutton").style.display = "";
                if (!grecaptcha.getResponse()) {
                    captcha();
                }
            }
        }
    }
    xmlhttp.open("POST", "/Controller?action=Captcha&response=" + res + "&mail=" + mail + "&user=" + name, true);
    xmlhttp.send();
}