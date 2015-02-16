<!DOCTYPE html>
<html lang="en"><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Admin</title>

    <!-- Bootstrap Core CSS -->
    <link href="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/bootstrap.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/metisMenu.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/font-awesome.css" rel="stylesheet" type="text/css">

    <link rel="stylesheet" href="./Image vector layer example_files/ol.css" type="text/css">
  
    <link rel="stylesheet" href="./Image vector layer example_files/layout.css" type="text/css">
   

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body style="padding:0">
    <script>
        var xmlhttp = new XMLHttpRequest();
        var url = "./Image vector layer example_files/constituency.json";
        var constituency;
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                var myArr = JSON.parse(xmlhttp.responseText);
                constituency = myArr;
            }
        }
        xmlhttp.open("GET", url, true);
        xmlhttp.send();
    </script>    

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <a class="navbar-brand" href="index.php">eSansad Admin - Ministry Of Statistics & Programme Implementation</a>
            </div>
            <!-- /.navbar-header -->

            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav in" id="side-menu">
                        <li>
                            <a class="active" href="index.php">Dashboard</a>
                        </li>
                        <li>
                            <a href="add_project.php">Add Project</a>
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>
        
           <div style="min-height: 380px;" id="page-wrapper">
            <div id="project" style="display: None" >
                <div class="row">
                    <a onclick = "goBack()"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>Back</a>
            </div>    
            </div>
            <div id="browser"> 
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header">Dashboard</h2>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                                 Select a state
                            
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="map" id = "map">

                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                </div>    
                <div class = "col-lg-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                                 <div id="info"> &nbsp;</div>
                                 
                            
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="row">                            
                                <select id = "constituencyList" onchange="renderPieChart()" style="padding:5px;margin:5px;">
                                    
                                </select>
                            </div> 
                            <div class="row">
                                <div id="piechart" style = "height: 300px; width: 500px"></div>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                </div>    
            <!-- /.row -->
            </div>
        <!-- /#page-wrapper -->
            

            <!-- Performers -->
            <div class="row">
            <div class="col-lg-6">
            <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-comments fa-fw"></i>
                            <strong>Top 5 Performers</strong>
                            <div class="icon-thumbs-up"></div>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <ul class="chat">
                                <li class="left clearfix">
                                    <span class="chat-img pull-left">
                                        <img src="http://164.100.47.132/mpimage/photo/4754.jpg" style="width:60px; height: 60px" alt="User Avatar" class="img-circle">
                                    </span>
                                    <div class="chat-body clearfix">
                                        <div class="pull-right">
                                            <div><strong>Total Allocated: </strong> 50000000 </div>
                                            <div><strong>Total Available: </strong> 46000000</div>
                                            <div><strong>Total Projects: </strong> 24</div>

                                        </div>
                                        <div class="header">
                                            <strong class="primary-font">Narendra Sawaikar</strong>
                                        </div>

                                        <p>
                                            <div><strong>Constituency: </strong> South Goa (Goa) </div>
                                            <div><strong>Party: </strong> BJP</div>

                                        </p>
                                    </div>
                                </li>
                                <li class="left clearfix">
                                    <span class="chat-img pull-left">
                                        <img src="http://164.100.47.132/mpimage/photo/273.jpg" alt="User Avatar" style="width:60px; height: 60px" class="img-circle">
                                    </span>
                                    <div class="chat-body clearfix">
                                         <div class="pull-right">
                                            <div><strong>Total Allocated: </strong> 50000000 </div>
                                            <div><strong>Total Available: </strong> 41000000</div>
                                            <div><strong>Total Projects: </strong> 20</div>

                                        </div>
                                        <div class="header">
                                            <strong class="primary-font">Shripad Naik</strong>
                                        </div>
                                        <p>
                                            <div><strong>Constituency: </strong> North Goa (Goa)</div>
                                            <div><strong>Party: </strong> BJP </div>
                                        </p>
                                    </div>
                                </li>
                                <li class="left clearfix">
                                    <span class="chat-img pull-left">
                                        <img src="http://164.100.47.132/mpimage/photo/4143.jpg" alt="User Avatar" style="width:60px; height: 60px" class="img-circle">
                                    </span>
                                    <div class="chat-body clearfix">
                                        <div class="pull-right">
                                            <div><strong>Total Allocated: </strong> 50000000 </div>
                                            <div><strong>Total Available: </strong> 39000000</div>
                                            <div><strong>Total Projects: </strong> 16</div>

                                        </div>
                                        <div class="header">
                                            <strong class="primary-font">Adhalrao Patil,Shri Shivaji</strong>
                                        </div>
                                        <p>
                                            <div><strong>Constituency: </strong> Shirur (Maharashtra) </div>
                                            <div><strong>Party: </strong> Shiv Sena </div>
                                            
                                        </p>
                                    </div>
                                </li>
                                <li class="left clearfix">
                                    <span class="chat-img pull-left">
                                        <img src="http://164.100.47.132/mpimage/photo/4847.jpg" alt="User Avatar" style="width:60px; height: 60px" class="img-circle">
                                    </span>
                                    <div class="chat-body clearfix">
                                        <div class="pull-right">
                                            <div><strong>Total Allocated: </strong> 50000000 </div>
                                            <div><strong>Total Available: </strong> 32000000</div>
                                            <div><strong>Total Projects: </strong> 14</div>

                                        </div>
                                        <div class="header">
                                            <strong class="primary-font">Adhikari,Shri Deepak (Dev)</strong>
                                        </div>
                                        <p>
                                            <div><strong>Constituency: </strong> Ghatal (West Bengal) </div>
                                            <div><strong>Party: </strong> All India Trinamool Congress </div>
                                        </p>
                                    </div>
                                </li>
                                <li class="left clearfix">
                                    <span class="chat-img pull-left">
                                        <img src="http://164.100.47.132/mpimage/photo/4502.jpg" alt="User Avatar" style="width:60px; height: 60px" class="img-circle">
                                    </span>
                                    <div class="chat-body clearfix">
                                        <div class="pull-right">
                                            <div><strong>Total Allocated: </strong> 50000000 </div>
                                            <div><strong>Total Available: </strong> 300000000</div>
                                            <div><strong>Total Projects: </strong> 11</div>

                                        </div>
                                        <div class="header">
                                            <strong class="primary-font">Adhikari,Shri Sisir Kumar</strong>
                                        </div>
                                        <p>
                                            <div><strong>Constituency: </strong> Kanthi (West Bengal) </div>
                                            <div><strong>Party: </strong> All India Trinamool Congress </div>
                                        </p>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                        <!-- /.panel-footer -->
                    </div>



                    <div class="col-lg-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-comments fa-fw"></i>
                            <strong>Bottom 5 Performers</strong>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <ul class="chat">
                                <li class="left clearfix">
                                    <span class="chat-img pull-left">
                                        <img src="http://164.100.47.132/mpimage/photo/2664.jpg" style="width:60px; height: 60px" alt="User Avatar" class="img-circle">
                                    </span>
                                    <div class="chat-body clearfix">
                                        <div class="pull-right">
                                            <div><strong>Total Allocated: </strong> 90000000 </div>
                                            <div><strong>Total Available: </strong> 400000</div>
                                            <div><strong>Total Projects: </strong> 2</div>

                                        </div>
                                        <div class="header">
                                            <strong class="primary-font">Anwar ,Shri Tariq</strong>
                                        </div>
                                        <p>
                                            <div><strong>Constituency: </strong> South Goa (Goa) </div>
                                            <div><strong>Party: </strong> BJP</div>

                                        </p>
                                    </div>
                                </li>
                                <li class="left clearfix">
                                    <span class="chat-img pull-left">
                                        <img src="http://164.100.47.132/mpimage/photo/25.jpg" alt="User Avatar" style="width:60px; height: 60px" class="img-circle">
                                    </span>
                                    <div class="chat-body clearfix">
                                        <div class="pull-right">
                                            <div><strong>Total Allocated: </strong> 90000000 </div>
                                            <div><strong>Total Available: </strong> 450000</div>
                                            <div><strong>Total Projects: </strong> 3</div>

                                        </div>
                                        <div class="header">
                                            <strong class="primary-font">Chaudhary,Shri Birendra Kumar</strong>
                                        </div>
                                        <p>
                                            <div><strong>Constituency: </strong> North Goa (Goa)</div>
                                            <div><strong>Party: </strong> BJP </div>
                                        </p>
                                    </div>
                                </li>
                                <li class="left clearfix">
                                    <span class="chat-img pull-left">
                                        <img src="http://164.100.47.132/mpimage/photo/4736.jpg" alt="User Avatar" style="width:60px; height: 60px" class="img-circle">
                                    </span>
                                    <div class="chat-body clearfix">
                                        <div class="pull-right">
                                            <div><strong>Total Allocated: </strong> 50000000 </div>
                                            <div><strong>Total Available: </strong> 600000</div>
                                            <div><strong>Total Projects: </strong> 4</div>

                                        </div>
                                        <div class="header">
                                            <strong class="primary-font">Choubey,Shri Ashwini Kumar</strong>
                                        </div>
                                        <p>
                                            <div><strong>Constituency: </strong> Shirur (Maharashtra) </div>
                                            <div><strong>Party: </strong> Shiv Sena </div>
                                            
                                        </p>
                                    </div>
                                </li>
                                <li class="left clearfix">
                                    <span class="chat-img pull-left">
                                        <img src="http://164.100.47.132/mpimage/photo/4650.jpg" alt="User Avatar" style="width:60px; height: 60px" class="img-circle">
                                    </span>
                                    <div class="chat-body clearfix">
                                        <div class="pull-right">
                                            <div><strong>Total Allocated: </strong> 50000000 </div>
                                            <div><strong>Total Available: </strong> 9000000</div>
                                            <div><strong>Total Projects: </strong> 5</div>

                                        </div>
                                        <div class="header">
                                            <strong class="primary-font">Devi,Smt. Veena</strong>
                                        </div>
                                        <p>
                                            <div><strong>Constituency: </strong> Ghatal (West Bengal) </div>
                                            <div><strong>Party: </strong> All India Trinamool Congress </div>
                                        </p>
                                    </div>
                                </li>
                                <li class="left clearfix">
                                    <span class="chat-img pull-left">
                                        <img src="http://164.100.47.132/mpimage/photo/4862.jpg" alt="User Avatar" style="width:60px; height: 60px" class="img-circle">
                                    </span>
                                    <div class="chat-body clearfix">
                                        <div class="pull-right">
                                            <div><strong>Total Allocated: </strong> 50000000 </div>
                                            <div><strong>Total Available: </strong> 700000</div>
                                            <div><strong>Total Projects: </strong> 7</div>

                                        </div>
                                        <div class="header">
                                            <strong class="primary-font">Dubey,Shri Satish Chandra</strong>
                                        </div>
                                        <p>
                                            <div><strong>Constituency: </strong> Kanthi (West Bengal) </div>
                                            <div><strong>Party: </strong> All India Trinamool Congress </div>
                                        </p>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                        <!-- /.panel-footer -->
                    </div>
                </div>
            <!-- End performers-->
        </div> 
        

      </div>   
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/bootstrap.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/metisMenu.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/raphael-min.js"></script>
    <script src="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/morris.js"></script>
    <script src="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/morris-data.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/sb-admin-2.js"></script>

    <script src="./Image vector layer example_files/example-behaviour.js" type="text/javascript"></script>
    <script src="./Image vector layer example_files/loader.js" type="text/javascript"></script>
    <script type="text/javascript" src="./Image vector layer example_files/ol.js"></script>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript" src="./Image vector layer example_files/image-vector-layer.js"></script>
    




</body></html>