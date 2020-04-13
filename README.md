<p align="center">
    <a href="https://app.bitrise.io/app/f42a1e81bd77a859">
        <img src="https://app.bitrise.io/app/f42a1e81bd77a859/status.svg?token=TnM9D2t2Acm9d3J5cpSv0w&branch=main"
            alt="Build Status">
    </a>
    <a href="https://www.codacy.com/gh/Flinesoft/FitnessTracker-Android">
        <img src="https://api.codacy.com/project/badge/Grade/b283bc9b62c446b9a3166f707ae8f5a8"
             alt="Code Quality"/>
    </a>
    <a href="https://www.codacy.com/gh/Flinesoft/FitnessTracker-Android">
        <img src="https://api.codacy.com/project/badge/Coverage/b283bc9b62c446b9a3166f707ae8f5a8"
             alt="Coverage"/>
    </a>
    <a href="https://github.com/Flinesoft/FitnessTracker-Android/releases">
        <img src="https://img.shields.io/badge/Version-1.0.1-blue.svg"
             alt="Version: 1.0.1">
    </a>
    <a href="https://github.com/Flinesoft/FitnessTracker-Android/blob/main/LICENSE">
        <img src="https://img.shields.io/badge/License-GPL--3.0-lightgrey.svg"
             alt="License: GPL-3.0">
    </a>
    <br />
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
  <a href="https://community.flinesoft.com/c/fitness-tracker-app">Community Forum</a>
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
./gradlew jacocoTestReport && bash <(curl -Ls https://coverage.codacy.com/get.sh)
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

FitnessTracker was brought to you by [Cihat Gündüz](https://github.com/Jeehut) in his free time. If you want to thank me and support the development of this project, please **make a small donation on [PayPal](https://paypal.me/Dschee/5EUR)**. In case you also like my other [open source contributions](https://github.com/Flinesoft) and [articles](https://medium.com/@Jeehut), please consider motivating me by **becoming a sponsor on [GitHub](https://github.com/sponsors/Jeehut)** or a **patron on [Patreon](https://www.patreon.com/Jeehut)**.

Thank you very much for any donation, it really helps out a lot! 💯

## Contributing

Contributions are welcome. Feel free to open an issue on GitHub with your ideas or implement an idea yourself and post a pull request. If you want to contribute code, please try to follow the same syntax and semantic in your **commit messages** (see rationale [here](http://chris.beams.io/posts/git-commit/)). Also, please make sure to add an entry to the `CHANGELOG.md` file which explains your change.

## License
This project is released under the [GNU General Public License, version 3 (GPL-3.0)](http://opensource.org/licenses/GPL-3.0).
