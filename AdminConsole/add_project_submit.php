<?php


	//next example will insert new conversation
	//$service_url = 'http://10.244.25.41:8080/eSansad_Services/rest/states/'.$_POST["state"].'/constituencies/'.$_POST["constituency"].'/proposals?';
	$service_url = 'http://10.244.25.41:8080/eSansad_Services/rest/states/'.rawurlencode($_POST["state"]).'/constituencies/'.rawurlencode($_POST["constituency"]).'/proposals?';
	//$service_url = 'http://10.244.25.41:8080/eSansad_Services/rest/states/Karnataka/constituencies/Bagalkot/proposals?';
	echo $service_url;
	//$params = 'category=Sports&submitted_by=gourav_shenoy&description=Test description&title=TEST&status=proposed&submission_date=2015-02-07&submitted_by_email=shenoy.200@gmail.com&cost=1.8';
	$curl = curl_init($service_url);
	// $curl_post_data = array(
	//         'category' => 'test message',
	//         'title' => 'sometitle',
	//         'description' => 'desc',
	//         'submission_date' => date("Y - j - n "),
	//         'submitted_by' => 'admin'
	// );
	print_r($_FILES);
	curl_setopt($curl, CURLOPT_BINARYTRANSFER, true);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
	//curl_setopt($curl, CURLOPT_POST, true);
	curl_setopt($curl, CURLOPT_POSTFIELDS,
            "category=".$_POST['category']."&submitted_by=admin&description=".$_POST['description']."&title=".$_POST['title']."&status=completed&submission_date=2015-02-07&submitted_by_email=admin@sansad.nic.in&cost=".$_POST['cost']);
			//"category=Electricity Facility&submitted_by=Shripad Yesso Naik&description=It is of the utmost importance that we use the funds towards  Subsidised power for farmers. The current facilities are ina very bad shape.&title=Subsidised power for farmers&status=completed&submission_date=2015-02-07&submitted_by_email=sripadnaik@sansad.nic.in&cost=0.0342");
	echo "category=".$_POST['category']."&submitted_by=admin&description=".$_POST['description']."&title=".$_POST['title']."&status=completed&submission_date=2015-02-07&submitted_by_email=admin@sansad.nic.in&cost=".$_POST['cost'];
	
	//curl_setopt($curl, CURLOPT_POSTFIELDS, $curl_post_data);
	curl_setopt($curl, CURLOPT_HTTPHEADER, 
		array('token: c7af89b1-4f27-401f-bc67-a71fc13bb067'));

	//echo "-----".$curl;

	$curl_response = curl_exec($curl);

	echo "-------------".$curl_response;

	 $info = curl_getinfo($curl);
	 if($info && $info["http_code"] == 200) {
	 	curl_close($curl);
	 	header("Location: add_project.php?success=true");
	 } else {
	 	header("Location: add_project.php?success=false");
	 }

	 //print_r( $info);
	// if ($curl_response === false) {
	//     $info = curl_getinfo($curl);
	//     curl_close($curl);
	//     die('error occured during curl exec. Additioanl info: ' . var_export($info));
	// }
	curl_close($curl);



	//header("Location: add_project.php?success=true");
	$decoded = json_decode($curl_response);
	if (isset($decoded->response->status) && $decoded->response->status == 'ERROR') {
	    die('error occured: ' . $decoded->response->errormessage);
	}
	//echo 'response ok!';
	var_export($decoded->response);

?>