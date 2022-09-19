package com.qa.api.swagger.util;

import com.github.javafaker.Faker;
import com.qa.api.swagger.pojo.Category;
import com.qa.api.swagger.pojo.Pet;
import com.qa.api.swagger.pojo.Tag;

import java.util.ArrayList;
import java.util.Arrays;

public class TestDataGenerator {

    public static Pet GeneratePet(){
        Faker faker = new Faker();

        Category category = new Category(1, "dogs");
        ArrayList<String>photo = new ArrayList<>(Arrays.asList(faker.internet().image()));
        Tag tag = new Tag(1,"Mix Breed" );
        ArrayList<Tag> tags = new ArrayList<>(Arrays.asList(tag));
        return new Pet(12, category, "Olly", photo, tags, Status.ADOPTED.name());
    }

    public static Pet GenerateUpdatedPet(){
        Faker faker = new Faker();

        Category category = new Category(1, "dogs");
        ArrayList<String>photo = new ArrayList<>(Arrays.asList(faker.internet().image()));
        Tag tag = new Tag(1,"Mix German Shepard and American Pit-bull" );
        ArrayList<Tag> tags = new ArrayList<>(Arrays.asList(tag));
        return new Pet(12, category, "OLLY", photo, tags, Status.SOLD.name());
    }

}
