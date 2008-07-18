package org.mule.providers.legstar.sample;

import java.util.ArrayList;
import java.util.List;

public class JVMQueryReply {
    private List <String> mEnvVarValues = new ArrayList <String>();
    private String mFormattedDate;
    private String mCountry;
    private String mLanguage;
    private String mCurrencySymbol;

    /**
     * @return the environment variable values to get
     */
    public final List <String> getEnvVarValues() {
        return mEnvVarValues;
    }

    /**
     * @param envVarValues the the environment variable values to set
     */
    public final void setEnvVarValues(List<String> envVarValues) {
        mEnvVarValues = envVarValues;
    }

    /**
     * @return the Formatted Date
     */
    public final String getFormattedDate() {
        return mFormattedDate;
    }

    /**
     * @param formattedDate the Formatted Date to set
     */
    public final void setFormattedDate(String formattedDate) {
        mFormattedDate = formattedDate;
    }

    /**
     * @return the country
     */
    public final String getCountry() {
        return mCountry;
    }

    /**
     * @param country the country to set
     */
    public final void setCountry(String country) {
        mCountry = country;
    }

    /**
     * @return the language
     */
    public final String getLanguage() {
        return mLanguage;
    }

    /**
     * @param mlanguage the language to set
     */
    public final void setLanguage(String mlanguage) {
        this.mLanguage = mlanguage;
    }

    /**
     * @return the Currency Symbol
     */
    public final String getCurrencySymbol() {
        return mCurrencySymbol;
    }

    /**
     * @param currencySymbol the Currency Symbol to set
     */
    public final void setCurrencySymbol(String currencySymbol) {
        mCurrencySymbol = currencySymbol;
    }

}
