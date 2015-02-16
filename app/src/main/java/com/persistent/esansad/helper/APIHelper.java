package com.persistent.esansad.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.persistent.esansad.AadharActivity;
import com.persistent.esansad.MainActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pranav_tendolkar on 2/7/2015.
 */
public class APIHelper {
    public static String TOKEN="32d0eb59-f7d5-4d0a-b7ac-3643144a1a0d";

    public static MemberofParliament getMP(final Context context, String state, String constituency) {
        Log.w("PRANAV", "reached getMP");

        MemberofParliament mp = new MemberofParliament();
        // Read from disk using FileInputStream
        FileInputStream f_in = null;
        try {
            Log.w("PRANAV", "create inputstream");

            f_in = context.openFileInput("mp.data");
            ObjectInputStream obj_in = new ObjectInputStream(f_in);

            mp = (MemberofParliament) obj_in.readObject();
            Log.w("PRANAV", "read mp object");
            Log.w("PRANAV", mp.getFirst_name());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.w("PRANAV", "Making ion request");
        String url = "http://10.244.25.41:8080/eSansad_Services/rest/states/" + state + "/constituencies/" + constituency + "/mp";
        url = url.replaceAll(" ", "%20");
        Log.w("PRANAV", url);
        try {
            Ion.with(context)
                    //.load("http://10.244.25.39:9001/")
                    .load(url)
                    .setHeader("token", TOKEN)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            MemberofParliament mp = new MemberofParliament();
                            mp.setExpenditure_incurred(result.get("expenditure_incurred").getAsDouble());
                            mp.setState(result.get("state").getAsString());
                            mp.setEducation_details(result.get("education_details").getAsString());
                            mp.setTotal(result.get("amount_available_with_interest").getAsDouble());
                            mp.setUnspent_balance(result.get("unspent_balance").getAsDouble());
                            mp.setParty(result.get("party").getAsString());
                            mp.setConstituency(result.get("constituency").getAsString());
                            mp.setEmail(result.get("email").getAsString());
                            mp.setFirst_name(result.get("first_name").getAsString());
                            mp.setLast_name(result.get("last_name").getAsString());

                            // do stuff with the result or error
                            // Write to disk with FileOutputStream
                            FileOutputStream f_out = null;
                            try {
                                f_out = context.openFileOutput("mp.data", context.MODE_WORLD_READABLE);
                                ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
                                obj_out.writeObject(mp);
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            catch (NullPointerException nu)
                            {
                                nu.printStackTrace();
                            }


                        }
                    });
        } catch (NullPointerException nu) {
            Log.e("ESANSAD", nu.getMessage().toString());
        }
        if (mp.getFirst_name() == null) {
            MemberofParliament member = new MemberofParliament();
            member.setEducation_details("10th pass");
            member.setEmail("pranav@tendolkar.in");
            member.setExpenditure_incurred(3.2);
            member.setFirst_name("Pranav");
            member.setLast_name("Tendokar");
            member.setParty("forca Goa");
            member.setConstituency("Verna");
            member.setUnspent_balance(2.7);
            member.setTotal(6.9);
            member.setState("Goa");
            return member;
        }


        return mp;
//        //dummy data

    }




    public static void postAadharData(final Context context,String xmlData,Intent loginIntent){
        String url = "http://10.244.25.41:8080/eSansad_Services/rest/users/aadhar";
        Log.w("PRANAV", "in postAadharData Helper Function");
        Log.w("PRANAV", "XMLDATA = "+xmlData);
        Ion.with(context).load(url)
                .setHeader("Content-Type", "text/xml")
                .setHeader("Accept", "application/json")
                .setHeader("token","c7af89b1-4f27-401f-bc67-a71fc13bb067")
                .setStringBody(xmlData)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.w("PRANAV", "Getting Adhaar details");
                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("email", result.get("email").getAsString());
                        edit.putString("apiKey", result.get("apiKey").getAsString());
                        edit.putBoolean("isMP", result.get("isMP").getAsBoolean());
                        edit.putBoolean("isReadOnly", result.get("isReadOnly").getAsBoolean());
                       // edit.putString("dist", result.get("apiKey").getAsJsonObject().get("aadhar_details").getAsString());

                        Log.w("PRANAV", "Aadhar successfull");
                        edit.commit();


//                        Intent mainActivity = new Intent(context,MainActivity.class);
//                        if()
                        Log.d("PRANAV","Adhar details == "+result.get("email").getAsString()+" ,"+result.get("apiKey").getAsJsonObject().get("aadhar_details").getAsString());


                    }
                });
    }




    public static void register(final Context context, String username, String password) throws Exception {

        String url = "http://10.244.25.41:8080/eSansad_Services/rest/users/register";
        try {




            Ion.with(context)
                    .load(url)
                    .setHeader("Accept", "application/json")
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .setBodyParameter("username", username)
                    .setBodyParameter("password", password)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (result != null) {
                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                                SharedPreferences.Editor edit = pref.edit();
                                edit.putString("email", result.get("email").getAsString());
                                edit.putString("mp_email", result.get("email").getAsString());
                                edit.putString("apiKey", result.get("apiKey").getAsString());
                                edit.putBoolean("isMP", result.get("isMP").getAsBoolean());
                                edit.putBoolean("isReadOnly", result.get("isReadOnly").getAsBoolean());
                                Log.w("PRANAV", "Sign up successfull");
                                edit.commit();


                                Intent i = new Intent(context,AadharActivity.class);

                                i.putExtra("email",result.get("email").getAsString());

                                context.startActivity(i);

                            } else {
                                Log.d("PRANAV", "exception raised for login");
                                Intent i = new Intent(context, MainActivity.class);
                                 // i.putExtra("name","Pranav Tendolkar");
                                i.putExtra("email","pranavtalking@gmail.com");
                                //i.putExtra("profileImage",personPhotoUrl);
                                context.startActivity(i);

                            }

                        }
                    });
        } catch (Exception ex) {
            Log.e("Esansad", "Manish!!!! Nullpointer aailo maka!!! :P");
            throw new Exception("Exception in Sign Up");
        }

    }

    public static List<Proposal> getAllProposals(final Context context, String state, String constituency) {
        return getProposals(context, state, constituency, "", "");
    }


    public static List<Proposal> getProposalsByCategory(final Context context, String state, String constituency, String category) {
        return getProposals(context, state, constituency, "", category);
    }

    public static List<Proposal> getProposalsBySelf(final Context context, String state, String constituency) {
        return getProposals(context, state, constituency, "self", "");
    }

    public static List<Proposal> getProposalsByCategoryAndSelf(final Context context, String state, String constituency, String category) {
        return getProposals(context, state, constituency, "self", category);
    }

    public static List<Proposal> getProposalsByCategoryAndMP(final Context context, String state, String constituency, String category) {
        return getProposals(context, state, constituency, "mp", category);
    }


    public static List<Proposal> getProposalsByMp(final Context context, String state, String constituency) {
        return getProposals(context, state, constituency, "mp", "");
    }


    public static List<Proposal> getProposals(final Context context, String state, String constituency, String by, String category) {

        Log.w("PRANAV", "reached getMP");

        List<Proposal> proposals = new ArrayList<Proposal>();
        // Read from disk using FileInputStream
        FileInputStream f_in = null;
        try {
            Log.w("PRANAV", "create inputstream");

            f_in = context.openFileInput("proposal.data");
            ObjectInputStream obj_in = new ObjectInputStream(f_in);
            Proposal prop = new Proposal();
            while ((prop = (Proposal) obj_in.readObject()) != null) {
                proposals.add(prop);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = "";
        Log.w("PRANAV", "Making ion request");
        if (by.equals("")) {
            url = "http://10.244.25.41:8080/eSansad_Services/rest/states/" + state + "/constituencies/" + constituency + "/proposals";
        } else {
            if (by.toLowerCase().equals("mp")) {
                url = "http://10.244.25.41:8080/eSansad_Services/rest/states/" + state + "/constituencies/" + constituency + "/proposals?from=" + "mp";
            } else if (by.toLowerCase().equals("self")) {
                url = "http://10.244.25.41:8080/eSansad_Services/rest/states/" + state + "/constituencies/" + constituency + "/proposals?from=" + "self";
            } else {
                throw new IllegalArgumentException("This method only takes mp or self");
            }
        }


        if (category.equals("")) {
            url = url;
        } else {
            if (category.indexOf('?') != -1) {
                url += "&category=" + category;
            } else {
                url += "?category=" + category;
            }
        }


        Log.w("Pranav_url", url);
        url = url.replaceAll(" ", "%20");
        Log.w("PRANAV", url);

        try {

            Ion.with(context)
                    //.load("http://10.244.25.39:9001/")
                    .load(url)
                    .setHeader("token", TOKEN)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result2) {
//
                            //Log.w("PRANAV",result);
                            List<Proposal> list = new ArrayList<Proposal>();
                            try {

                                JsonArray result = result2.get("proposals").getAsJsonArray();
                                Iterator<JsonElement> it = result.iterator();
                                while (it.hasNext()) {
                                    Proposal prop = new Proposal();


                                    JsonObject obj = it.next().getAsJsonObject();
                                    prop.setTitle(obj.get("title").getAsString());
                                    prop.setCategory(obj.get("category").getAsString());
                                    prop.setDescription(obj.get("description").getAsString());
                                    prop.setCost(Double.parseDouble(obj.get("cost").getAsString()));
                                    prop.setConstituency(obj.get("constituency").getAsString());
                                    prop.setSubmission_date(obj.get("submission_date").getAsString());
                                    prop.setSubmitted_by(obj.get("submitted_by").getAsString());
                                    prop.setUpvotes(Integer.parseInt(obj.get("upvotes").getAsString()));
                                    prop.setDownvotes(Integer.parseInt(obj.get("downvotes").getAsString()));
                                    prop.setSubmitted_by_email(obj.get("submitted_by_email").getAsString());
                                    prop.setProposal_id(obj.get("proposal_id").getAsString());
                                    list.add(prop);

                                }


                            } catch (Exception ex) {
                                ex.printStackTrace();

                            }


                            //add to filesystem


                            // Write to disk with FileOutputStream
                            FileOutputStream f_out = null;
                            try {
                                f_out = context.openFileOutput("proposal.data", context.MODE_WORLD_READABLE);
                                ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
                                for (Proposal item : list) {
                                    obj_out.writeObject(item);
                                }
                                obj_out.close();
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }


                        }

                    });
        } catch (NullPointerException ex) {
            Log.e("Esansad", "Manish!!!! Nullpointer aailo maka!!! :P");
        }


        if (proposals.isEmpty())

        {

            List<Proposal> proposals2 = new ArrayList<Proposal>();

            Proposal p = new Proposal();
            p.setTitle("Buildings for sports activities");
            p.setCategory("Sports");
            p.setStatus("proposed");
            p.setDescription("Lets dedicate some money towards Buildings for sports activities. Money spent on this will greatly benefit the people of the constituency.");
            p.setConstituency("North Goa");
            p.setSubmission_date("2014-12-18");
            p.setDownvotes(792);
            p.setSubmitted_by("Dharm-mitra Ramaswamy");
            p.setUpvotes(645);
            p.setCost(0.2282);
            p.setSubmitted_by_email("dharma@gmail.com");
            proposals2.add(p);

            p = new Proposal();
            p.setTitle("Creches and Anganwadies");
            p.setCategory("Health And Family Welfare");
            p.setStatus("proposed");
            p.setDescription("We can have some Creches and Anganwadies. Money spent on this will greatly benefit the people of the constituency.");
            p.setConstituency("North Goa");
            p.setSubmission_date("2014-10-27");
            p.setDownvotes(534);
            p.setSubmitted_by("Ananya Sethi");
            p.setUpvotes(512);
            p.setCost(0.8586);
            proposals2.add(p);

            p = new Proposal();
            p.setTitle("Buildings for sports activities");
            p.setCategory("Sports");
            p.setSubmitted_by_email("AjiteshParmar@gmail.com");
            p.setStatus("proposed");
            p.setDescription("We can have some Buildings for sports activities. the people desperately need this");
            p.setConstituency("North Goa");
            p.setSubmission_date("2014-12-18");
            p.setDownvotes(359);
            p.setSubmitted_by("Ajitesh Parmar");
            p.setUpvotes(52);
            p.setCost(0.2282);
            proposals2.add(p);

            return proposals2;


        }


        else {
            return proposals;
        }

    }


    public static List<Proposal> getAllProjects(final Context context, String state, String constituency) {

        Log.w("PRANAV", "reached getMP");
        List<Proposal> proposals = new ArrayList<Proposal>();
        // Read from disk using FileInputStream
        FileInputStream f_in = null;
        try {
            Log.w("PRANAV", "create inputstream");

            f_in = context.openFileInput("projects.data");
            ObjectInputStream obj_in = new ObjectInputStream(f_in);
            Proposal prop = new Proposal();
            while ((prop = (Proposal) obj_in.readObject()) != null) {
                proposals.add(prop);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.w("PRANAV", "Making ion request");
        String url = "http://10.244.25.41:8080/eSansad_Services/rest/states/" + state + "/constituencies/" + constituency + "/projects";
        Log.w("Pranav_url", url);
        url = url.replaceAll(" ", "%20");
        Log.w("PRANAV", url);

        Ion.with(context)
                //.load("http://10.244.25.39:9001/")
                .load(url)
                .setHeader("token", TOKEN)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result2) {
//
                        //Log.w("PRANAV",result);

                        JsonArray result = result2.get("projects").getAsJsonArray();
                        List<Proposal> list = new ArrayList<Proposal>();
                        Iterator<JsonElement> it = result.iterator();
                        while (it.hasNext()) {
                            Proposal prop = new Proposal();

                            try {
                                JsonObject obj = it.next().getAsJsonObject();
                                prop.setTitle(obj.get("title").getAsString());
                                prop.setCategory(obj.get("category").getAsString());
                                prop.setDescription(obj.get("description").getAsString());
                                prop.setConstituency(obj.get("constituency").getAsString());
                                prop.setSubmission_date(obj.get("submission_date").getAsString());
                                prop.setSubmitted_by(obj.get("submitted_by").getAsString());
                                prop.setUpvotes(Integer.parseInt(obj.get("upvotes").getAsString()));
                                prop.setDownvotes(Integer.parseInt(obj.get("downvotes").getAsString()));
                                prop.setCost(Double.parseDouble(obj.get("cost").getAsString()));
                                prop.setSubmitted_by_email(obj.get("submitted_by_email").getAsString());
                                prop.setProposal_id(obj.get("proposal_id").getAsString());

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            list.add(prop);
                        }

                        //add to filesystem


                        // Write to disk with FileOutputStream
                        FileOutputStream f_out = null;
                        try {
                            f_out = context.openFileOutput("projects.data", context.MODE_WORLD_READABLE);
                            ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
                            for (Proposal item : list) {
                                obj_out.writeObject(item);
                            }
                            obj_out.close();
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }


                    }

                });


        if (proposals.isEmpty())

        {

            List<Proposal> proposals2 = new ArrayList<Proposal>();
            for (int i = 0; i < 7; i++) {
                Proposal proposal = new Proposal();
                proposal.setConstituency("Verna");
                proposal.setCategory("Chocolates");
                proposal.setCost(2);
                proposal.setDescription("Kitkats for all!");
                proposal.setDownvotes(10 + i);
                proposal.setTitle("Give us some kitkats");
                proposal.setStatus("completed");
                proposal.setUpvotes(1000 - i);
                proposal.setSubmission_date("2015-02-07");

                proposals2.add(proposal);
            }

            return proposals2;
        } else {
            return proposals;
        }

    }


    public static void submitProposal(Context context, Proposal proposal, String state,String image) {
        try {
            String url = "http://10.244.25.41:8080/eSansad_Services/rest/states/"+state+"/constituencies/"+proposal.getConstituency()+"/proposals";
            url=url.replace(" ", "%20");

            Ion.with(context)
                    .load(url)
                    .addHeader("token",TOKEN)
                    .setBodyParameter("category", proposal.getCategory())
                    .setBodyParameter("submitted_by", proposal.getSubmitted_by())
                    .setBodyParameter("description", proposal.getDescription())
                    .setBodyParameter("title", proposal.getTitle())
                    .setBodyParameter("status","proposed")
                            // .setBodyParameter("constituency", "bar")
                    .setBodyParameter("submission_date", proposal.getSubmission_date())
                    .setBodyParameter("submitted_by_email", proposal.getSubmitted_by_email())

                    .setBodyParameter("description", proposal.getDescription())
                    .setBodyParameter("cost", ""+proposal.getCost())
                    .setBodyParameter("image", ""+image)
                    .asString()
                    .setCallback(new FutureCallback<String>() {

                                     @Override
                                     public void onCompleted(Exception e, String result) {
                                         Log.w("PROPOSAL","ADDED NEW PROPOSAL "+ result);

                                     }
                                 }
                    );


            //insert into localfile


            FileOutputStream f_out = null;
            try {
                f_out = context.openFileOutput("proposal.data", context.MODE_WORLD_READABLE);
                AppendingObjectOutputStream out = new AppendingObjectOutputStream(f_out);
                out.writeObject( proposal);
                out.close();

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("ESANSAD", ex.getMessage().toString());
        }
    }


    public static void submitAadhar(Context context, Proposal proposal, String state) {
        try {
            String url = "http://10.244.25.41:8080/eSansad_Services/rest/states/"+state+"/constituencies/"+proposal.getConstituency()+"/proposals";
            url=url.replace(" ", "%20");

            Ion.with(context)
                    .load(url)
                    .addHeader("token",TOKEN)
                    .setBodyParameter("category", proposal.getCategory())
                    .asString()
                    .setCallback(new FutureCallback<String>() {

                                     @Override
                                     public void onCompleted(Exception e, String result) {
                                         Log.w("PROPOSAL","ADDED NEW PROPOSAL "+ result);

                                     }
                                 }
                    );


        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("ESANSAD", ex.getMessage().toString());
        }
    }




public static void vote(final Context context, final Proposal prop, final String state, String vote)
{
    String id=prop.getProposal_id();
    String url ="";

    url = "http://10.244.25.41:8080/eSansad_Services/rest/vote/"+prop.getProposal_id()+"/?type="+vote;

    Ion.with(context)
            .load(url)
            .setHeader("token", TOKEN)
            .asString()


            .setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    // do stuff with the result or error
                    Log.e("PRANAV:VOTERESULT:", result);

                    APIHelper.getAllProjects(context,state, prop.getConstituency());
                    APIHelper.getAllProposals(context,state,prop.getConstituency());


                }
            });



}



}


