package com.example.myapplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a question asked in the app.
 */
public class Question extends InteractablePost {

    /**
     * The responses posted on this question.
     */
    private final List<Response> responses = new ArrayList<>();


    private String bestAnswerId;

    private TagCollection tags = new TagCollection();
    /**
     * Create a new post.
     *
     * @param postID       the ID of the post
     * @param author       the author of the post
     * @param content      the content of the post
     * @param creationDate the date on which the post was created
     */
    public Question(String postID, User author, Content content,
                    Date creationDate, List<String> tags, int voteScore, String bestAnswerId) {
        super(postID, author, content, creationDate, voteScore);

        this.tags = new TagCollection(tags);
        this.bestAnswerId = bestAnswerId;
    }

    /**
     * Respond to this post.
     * TODO: Synchronize any responses added to prevent response duplication
     * @param response the response to respond with
     * @return true if the response was not already added
     */
    public boolean respond(Response response) {
        for (Response _response : responses) {
            if (_response.getPostID() == response.getPostID()) {
                return false;
            }
        }
        responses.add(response);
        return true;
    }

    public TagCollection getTags() {
        return this.tags;
    }

    public String getBestAnswerId() {
        return bestAnswerId;
    }

    public void setBestAnswerId(String bestAnswerId) {
        this.bestAnswerId = bestAnswerId;
    }

    public static Question fromDatabaseRecord(String id, QuestionDatabaseRecord record, User user) {
        return new Question(id,
                user,
                Content.fromDatabaseRecord(record.post.content),
                record.post.creationDate, record.tags,
                record.post.voteScore,
                record.bestAnswer);
    }
}
