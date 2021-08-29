/*
 * Copyright (C) 2021 Parisi Alessandro
 * This file is part of RAW4J (https://github.com/palexdev/RAW4J).
 *
 * RAW4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RAW4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with RAW4J.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.palexdev.raw4j.utils.filtering;

import io.github.palexdev.raw4j.data.Post;
import io.github.palexdev.raw4j.data.listing.PostList;
import io.github.palexdev.raw4j.enums.PostType;
import io.github.palexdev.raw4j.utils.filtering.base.AbstractFilterHelper;

import java.util.List;
import java.util.function.Predicate;

/**
 * Filter helper for {@link PostList}s, extends {@link AbstractFilterHelper}.
 */
public class PostListFilterHelper extends AbstractFilterHelper<PostList, Post> {
    //================================================================================
    // Properties
    //================================================================================
    private final PostList postList;

    //================================================================================
    // Constructors
    //================================================================================
    public PostListFilterHelper(PostList postList) {
        this.postList = postList;
    }

    public static PostListFilterHelper filtering(PostList postList) {
        return new PostListFilterHelper(postList);
    }

    //================================================================================
    // Predicates
    //================================================================================

    /**
     * @return a predicate that filters the list by archived comments
     */
    public static Predicate<Post> filterByArchived() {
        return Post::isArchived;
    }

    /**
     * @return a predicate that filters the list by the given author
     */
    public static Predicate<Post> filterByAuthor(String author) {
        return post -> post.getAuthor().equals(author);
    }

    /**
     * @return a predicate that filters the list by edited comments
     */
    public static Predicate<Post> filterByEdited() {
        return Post::hasBeenEdited;
    }

    /**
     * @return a predicate that filters the list by gallery posts
     */
    public static Predicate<Post> filterByGallery() {
        return Post::isGallery;
    }

    /**
     * @return a predicate that filters the list by nsfw comments
     */
    public static Predicate<Post> filterByNsfw() {
        return Post::isOver18;
    }

    /**
     * @return a predicate that filters the list by saved comments
     * @see Post#isSaved()
     */
    public static Predicate<Post> filterBySaved() {
        return Post::isSaved;
    }

    /**
     * @return a predicate that filters the list by the items for which the score
     * is lesser or equal to the given amount
     */
    public static Predicate<Post> filterByScoreGreaterEqual(int score) {
        return post -> post.getScore() >= score;
    }

    /**
     * @return a predicate that filters the list by the items for which the score
     * is greater or equal to the given amount
     */
    public static Predicate<Post> filterByScoreLesserEqual(int score) {
        return post -> post.getScore() <= score;
    }

    /**
     * @return a predicate that filters the list by the given subreddit names
     */
    public static Predicate<Post> filterBySubreddit(String... subreddits) {
        return post -> List.of(subreddits).contains(post.getSubreddit());
    }

    /**
     * @return a predicate that filters the list by the given post types
     * @see PostType
     */
    public static Predicate<Post> filterByTypes(PostType... types) {
        return post -> List.of(types).contains(post.getPostType());
    }

    /**
     * @return a predicate that filters the list by upvoted comments
     * @see Post#likes()
     */
    public static Predicate<Post> filterByUpvoted() {
        return Post::likes;
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public PostList filter() {
        if (getList() != null) {
            getList().removeIf(predicate);
        }
        return postList;
    }

    /**
     * @return the list of {@link Post}s
     */
    @Override
    public List<Post> getList() {
        return postList.posts();
    }
}
