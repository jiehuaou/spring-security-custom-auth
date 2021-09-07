package com.example.demo.util;

import org.hamcrest.Description;

public class StringContainMatcher implements org.hamcrest.Matcher<String>{
    final String text;

    public StringContainMatcher(String text) {
        this.text = text;
    }

    @Override
    public boolean matches(Object o) {
        return o!=null && o.toString().contains(text);
    }

    @Override
    public void describeMismatch(Object o, Description description) {

    }

    @Override
    public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

    }

    @Override
    public void describeTo(Description description) {

    }
}
