[![Build Status](https://app.bitrise.io/app/a585f74c9b21bdd8/status.svg?token=QqGgwBKQfDPO06vldoB52A)](https://app.bitrise.io/app/a585f74c9b21bdd8)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/38033a9a5d59407287d45fd12a21b392)](https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Flinesoft/FitnessTracker-Android&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/Flinesoft/FitnessTracker-Android/branch/main/graph/badge.svg?token=V9d0qNPZNn)](https://codecov.io/gh/Flinesoft/FitnessTracker-Android)

<p align="center">
    <a href="https://paypal.me/Dschee/5EUR">
        <img src="https://img.shields.io/badge/PayPal-Donate-orange.svg"
             alt="PayPal: Donate">
    </a>
    <a href="https://github.com/sponsors/Jeehut">
        <img src="https://img.shields.io/badge/GitHub-Become a sponsor-orange.svg"
             alt="GitHub: Become a sponsor">
    </a>
    <a href="https://patreon.com/Jeehut">
        <img src="https://img.shields.io/badge/Patreon-Become a patron-orange.svg"
             alt="Patreon: Become a patron">
    </a>
</p>

<p align="center">
  • <a href="https://community.flinesoft.com/c/fitness-tracker-app">Community Forum</a>
  • <a href="https://play.google.com/store/apps/details?id=com.flinesoft.fitnesstracker">Google Play</a>
  • <a href="#donation">Donation</a>
  • <a href="#contributing">Contributing</a>
  • <a href="#license">License</a>
</p>

# FitnessTracker-Android

This is the FitnessTracker app project for the Android platform.

## Getting Started

Just open the project in Android Studio 3.6+ and let Gradle do it's work. :)

## Code Coverage

You can get code coverage reports by just running:

```bash
./gradlew jacocoTestReport
```

## Screenshots

You can update the all the app screenshots by just running:

```bash
fastlane screengrab_variants
```

Or if you just need updated Play Store screenshots, instead run:

```bash
fastlane screengrab_variants class:SampleDataTest
```

## Donation

BartyCrouch was brought to you by [Cihat Gündüz](https://github.com/Jeehut) in his free time. If you want to thank me and support the development of this project, please **make a small donation on [PayPal](https://paypal.me/Dschee/5EUR)**. In case you also like my other [open source contributions](https://github.com/Flinesoft) and [articles](https://medium.com/@Jeehut), please consider motivating me by **becoming a sponsor on [GitHub](https://github.com/sponsors/Jeehut)** or a **patron on [Patreon](https://www.patreon.com/Jeehut)**.

Thank you very much for any donation, it really helps out a lot! 💯


## Contributing

Contributions are welcome. Feel free to open an issue on GitHub with your ideas or implement an idea yourself and post a pull request. If you want to contribute code, please try to follow the same syntax and semantic in your **commit messages** (see rationale [here](http://chris.beams.io/posts/git-commit/)).

## License
This project is released under the [GNU General Public License, version 3 (GPL-3.0)](http://opensource.org/licenses/GPL-3.0).
