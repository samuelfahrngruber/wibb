# wibb documentation & dev guidelines

This figure illustrates the basic idea and a rough mockup of the *wibb* UI:
![wibb concept](wibb-idea.png)

## Git
Here you can find information about any *Git* guidelines used in *wibb*.

### Basic rules:

* **Always** include a `.gitignore` file for project directories that could contain build files.

* Try to add an appropriate commit message when committing changes

### Git workflow:
There are three branches used in the *wibb* project:
* `master`
* `br-wibb-android`
* `br-wibb-ws`
These branches must be used according to the following figure:

![wibb git workflow](wibb-git-workflow.png)

in other words: changes to one of the main folders have to be committed to the corresponding branch like shown in this table:

| Folder       | commit to Branch |
|--------------|------------------|
| doc          | master           |
| img          | master           |
| other        | master           |
| wibb-android | br-wibb-android  |
| wibb-ws      | br-wibb-ws       |
| wibb-db      | master           |