	

    <?php
     
     
            //next example will insert new conversation
            
            //$service_url = 'http://10.244.25.41:8080/eSansad_Services/rest/states/Karnataka/constituencies/Bagalkot/proposals?';
            //$params = 'category=Sports&submitted_by=gourav_shenoy&description=Test description&title=TEST&status=proposed&submission_date=2015-02-07&submitted_by_email=shenoy.200@gmail.com&cost=1.8';
            //$curl = curl_init($service_url);
            // $curl_post_data = array(
            //         'category' => 'test message',
            //         'title' => 'sometitle',
            //         'description' => 'desc',
            //         'submission_date' => date("Y - j - n "),
            //         'submitted_by' => 'admin'
            // );
            //curl_setopt($curl, CURLOPT_HTTPHEADER, array('Accept: application/json', 'Content-Type: application/json'));
            //curl_setopt($curl, CURLOPT_BINARYTRANSFER, true);
            //curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
            //curl_setopt($curl, CURLOPT_POST, true);
           // curl_setopt($curl, CURLOPT_POSTFIELDS,
             //   "category=".$_POST['category']."&submitted_by=admin&description=".$_POST['description']."&title=".$_POST['title']."&status=completed&submission_date=2015-02-07&submitted_by_email=admin@sansad.nic.in&cost=".$_POST['cost']);
           
            //curl_setopt($curl, CURLOPT_POSTFIELDS, $curl_post_data);
            
            
            

            // $curl_response = curl_exec($curl);
            
            // $info = curl_getinfo($curl);
            
            // if ($curl_response === false) {
            //     $info = curl_getinfo($curl);
            //     curl_close($curl);
            //     die('error occured during curl exec. Additioanl info: ' . var_export($info));
            // }
            // curl_close($curl);
            // // $decoded = json_decode($curl_response);
            // // if (isset($decoded->response->status) && $decoded->response->status == 'ERROR') {
            // //     die('error occured: ' . $decoded->response->errormessage);
            // // }
            // // echo 'response ok!';
            // // var_export($decoded->response);
            // //echo $curl_response;
            // $json   = json_decode($curl_response);
            // $atoken = $json->access_token;
            // echo $atoken;
            $service_url = 'http://10.244.25.41:8080/eSansad_Services/rest/states/'.urlencode($_REQUEST['state']).'/constituencies/'.urlencode($_REQUEST['constituency']).'/mp/balance';

            $ch = curl_init();

            $atoken = 'c7af89b1-4f27-401f-bc67-a71fc13bb067';
            curl_setopt($ch, CURLOPT_URL, $service_url);
            curl_setopt($ch, CURLOPT_HTTPHEADER, array('token:'.$atoken));
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
            //curl_setopt($ch, CURLOPT_POST, 1);
            //curl_setopt($ch, CURLOPT_COOKIEFILE, $cookie_file);

            $curl_response = curl_exec($ch);

            //$info = curl_getinfo($ch);
            //var_dump($info);
           echo $curl_response;
            curl_close($ch);
     
    ?>

