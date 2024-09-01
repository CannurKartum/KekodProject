

[Switch Project](https://github.com/user-attachments/assets/79c14d03-5f45-4ef9-a1c4-6b116ccce101)

**Kekod Project**!

KekodProject is an Android application that demonstrates the dynamic management of a BottomNavigationView using ViewModel and LiveData. The app allows users to toggle visibility and add or remove items from the BottomNavigationView dynamically through switches in a fragment (EgoFragment). The state management ensures a seamless user experience even when navigating between fragments.
Features

**1. Dynamic BottomNavigationView Management**
        Add/Remove Items: Users can add or remove items from the BottomNavigationView through switches located in the EgoFragment.
        Visibility Control: The visibility of the BottomNavigationView is dynamically managed based on the "ego" switch state:
        When "ego" is switched ON, the BottomNavigationView is hidden.
        When "ego" is switched OFF, the BottomNavigationView becomes visible again.

**2. State Management with ViewModel and LiveData**
    The app uses ViewModel to maintain UI-related data in a lifecycle-conscious way.
    LiveData is utilized to observe changes in the state and update the UI automatically.

**3. Navigation Component Integration**
    The app leverages Android's Navigation Component to manage navigation between different fragments seamlessly.
    BottomNavigationView is integrated with the Navigation Component to navigate between fragments while keeping the state intact.

**4. User Feedback with Toast Messages**
    A Toast message is displayed if the user attempts to add more than 5 items to the BottomNavigationView, ensuring a user-friendly experience.


**Project Structure**
    ├── app
│   ├── src/main
│   │   ├── java/com/example/kekodproject
│   │   │   ├── MainActivity.kt                   # Main activity handling BottomNavigationView and ViewModel setup
│   │   │   ├── MainActivityViewModel.kt          # ViewModel managing the state of BottomNavigationView and switches
│   │   │   └── ui
│   │   │       └── EgoFragment.kt                # Fragment containing switches to manage BottomNavigationView items
│   │   └── res
│   │       ├── layout
│   │       │   ├── activity_main.xml             # Layout file for MainActivity
│   │       │   └── fragment_ego.xml              # Layout file for EgoFragment
│   │       └── drawable                          # Icons for BottomNavigationView items
│   └── build.gradle                              # Project build configuration
