# CoderSchoolTODOApp
A TodoApp to Confirm that I am eligible to participate CoderSchool Course

 TodoApp is an android app that allows building a todo list and basic …

…todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Hoang Phat Nguyen - lazybee27102@gmail.com

Time spent: 30 hours spent in total 

The following functionality is completed:

* [X] User can successfully add and remove items from the todo list
* [X] User can tap a todo item in the list and bring up an edit screen for the todo item and then have any changes to the text reflected in the todo list.
* [X] User can persist todo items and retrieve them properly on app restart

The following optional features are implemented:

* [X] Persist the todo items [into SQLiteinstead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](using RecyclerView with CustomAdapter and ViewHolder)
* [X] Add support for completion due dates for todo items (items can be change to be done or not yet)
* [X] Use a [DialogFragment] instead of new Activity for editing items
* [X] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

- Use RecyclerView with StaggeredGridLayoutManager and customize the Adapter with RecyclerView.ViewHolder pattern
- Every note can have style 'strikethrough' to ensure they have been done by User
- Add some functions to each note:
+ Add multiple links (Using ListView with multiple choices,with Add,Delete,Open Browser each link)
+ Image (Using ContentResolver,Intent,Uri to Add,Delete Iamge)
+ Color (ChangeColor,DefaultColor)
+ Deadline (Using DatePicker,TimePicker,Add,Delete)

Here's a walkthrough of implemented user stories:
* [X] OrignalLink: http://i.imgur.com/QxZzgbn.gifv
* [X] Markdown Link:[Imgur](http://i.imgur.com/QxZzgbn.gifv)




GIF created with [LiceCap](http://www.cockos.com/licecap/).

# Notes
* [X] It's difficult to design an app(I never do it before,it hard to choose a color,layout,background,....)
* [X] There is some issues with RecyclerView that GoogleAndroid Team haven't fixed yet
* [X] Android Marshmalow requires a new User Permission,so I am not sure that all the function run well with device which runs Android 6.0
* [X] There is some bugs that I don't know How it doesn't work.

