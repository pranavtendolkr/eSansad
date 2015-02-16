package com.persistent.esansad;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.persistent.esansad.helper.APIHelper;
import com.persistent.esansad.helper.Proposal;


public class ProposalDetailsActivity extends FragmentActivity {

    protected TextView tvTitle;
    protected TextView tvDescription;
    protected TextView tvCategory;
    protected TextView tvConstituency;
    protected TextView tvEstimate;
    protected TextView tvDuration;
    protected ImageView tvImage;

    protected Proposal proposal;

    protected ImageButton like;
    protected ImageButton dislike;
    protected TextView likeText;
    protected TextView dislikeText;

    protected Button askMp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_details);
        createProposalDetails();
        //getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public ProposalDetailsActivity(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public ProposalDetailsActivity(TextView tvTitle, Proposal p) {
        this.tvTitle = tvTitle;
        this.proposal = p;
    }

    public ProposalDetailsActivity() {
        super();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_proposal_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createProposalDetails() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvConstituency = (TextView) findViewById(R.id.tvConstituency);
        tvEstimate = (TextView) findViewById(R.id.tvEstimate);
        //tvDuration = (TextView) findViewById(R.id.tvDuration);
        tvImage = (ImageView) findViewById(R.id.tvImage);
        Intent myIntent = getIntent();
        proposal = (Proposal) myIntent.getSerializableExtra("proposal");
        //Get info
        tvTitle.setText(this.proposal.getTitle());
        tvDescription.setText(this.proposal.getDescription());
        tvCategory.setText(this.proposal.getCategory());
        tvConstituency.setText(this.proposal.getConstituency());
        tvEstimate.setText(String.valueOf(this.proposal.getCost()));
        //tvDuration.setText(String.valueOf(proposal.getSubmission_date()));
        askMp =(Button) findViewById(R.id.btnAskMP);

        like = (ImageButton) findViewById(R.id.btnLike);
        dislike = (ImageButton) findViewById(R.id.btnDislike);
        likeText = (TextView) findViewById(R.id.tvProposalLikes);
        dislikeText = (TextView) findViewById(R.id.tvProposalDislikes);

        likeText.setText(String.valueOf(proposal.getUpvotes()));
        dislikeText.setText(String.valueOf(proposal.getDownvotes()));
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeText.setText(String.valueOf(Integer.parseInt(likeText.getText().toString()) + 1));
                like.setEnabled(false);
                dislike.setEnabled(false);
                like.setBackgroundColor(getResources().getColor(R.color.green));
                APIHelper.vote(getApplication(),proposal,"goa","up");
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dislikeText.setText(String.valueOf(Integer.parseInt(dislikeText.getText().toString()) + 1));
                dislike.setEnabled(false);
                dislike.setBackgroundColor(getResources().getColor(R.color.red));
                like.setEnabled(false);
                APIHelper.vote(getApplication(),proposal,"goa","down");
            }
        });

        askMp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"A notification has been sent to your MP",Toast.LENGTH_LONG).show();
            }
        });

        if (proposal.getTitle().toLowerCase().contains("pool")) {
            Ion.with(tvImage)

                            // .animateLoad(spinAnimation)
                            // .animateIn(fadeInAnimation)
                    .load("http://www.thehindu.com/multimedia/dynamic/01643/TV06SWIMMINGPOO_TV_1643296e.jpg");
        }else if(proposal.getTitle().toLowerCase().contains("road")){
            Ion.with(tvImage)

                            // .animateLoad(spinAnimation)
                            // .animateIn(fadeInAnimation)
                    .load("http://www.navabharat.com/wp-content/uploads/2011/09/Bad-Condition-of-road-in-india.jpg");
        }
    }
}
