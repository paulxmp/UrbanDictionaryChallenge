# UrbanDictionaryChallenge
Urban Dictionary test app in kotlin for Nike

App begins with a SearchView to get "term" User wants to get definition of fronm Urban Dictionary.
Retrieved term definitions can be sorted by Thumbs Up votes or Thumbs Down votes.
App checks if the term has already been retrieved before making a new API call. If the
term has been searched then the cached definitions are used.

Searched terms are stored locally in a HashMap

Menu item allows User to toggle ON/OFF a listview with previously searched terms and select from 
the list. Once a term is selected from the list, the listview visibility is set to GONE.

Orientation changes are handled.
