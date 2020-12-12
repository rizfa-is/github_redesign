package com.istekno.githubredesign.db

import com.istekno.githubredesign.model.Content
import com.istekno.githubredesign.model.DeveloperDetail

object MainData {
    private val developerName = arrayOf(
        "Jake Wharton",
        "Amit Shekhar",
        "Romain Guy",
        "Chris Banes",
        "David",
        "Ravi Tamada",
        "Deny Prasetyo",
        "Budi Oktaviyan",
        "Hendi Santika",
        "Sidiq Permana"
    )

    private val developerUsername = arrayOf(
        "JakeWharton",
        "amitshekhariitbhu",
        "romainguy",
        "chrisbanes",
        "tipsy",
        "ravi8x",
        "jasoet",
        "budioktaviyan",
        "hendisantika",
        "sidiqpermana"
    )

    private val developerLocation = arrayOf(
        "Pittsburgh, PA, USA",
        "New Delhi, India",
        "California",
        "Sydney, Australia",
        "Trondheim, Norway",
        "India",
        "Kotagede, Yogyakarta, Indonesia",
        "Jakarta, Indonesia",
        "Bojongsoang - Bandung Jawa Barat",
        "Jakarta Indonesia"
    )

    private val developerCompany = arrayOf(
        "Google, Inc.",
        "MindOrksOpenSource",
        "Google",
        "Google working on @android",
        "Working Group Two",
        "AndroidHive | Droid5",
        "gojek-engineering",
        "KotlinID",
        "JVMDeveloperID @KotlinID @IDDevOps",
        "Nusantara Beta Studio"
    )

    private val developerAvatar = arrayOf(
        "https://avatars0.githubusercontent.com/u/66577?s=400&v=4",
        "https://avatars1.githubusercontent.com/u/9877145?s=460&u=dc129425e403bffd62c59b2840eb6603978966d1&v=4",
        "https://avatars1.githubusercontent.com/u/869684?s=460&v=4",
        "https://avatars1.githubusercontent.com/u/227486?s=400&v=4",
        "https://avatars.githubusercontent.com/u/1521451?v=3",
        "https://avatars3.githubusercontent.com/u/497670?s=460&v=4",
        "https://avatars0.githubusercontent.com/u/363917?s=400&u=6401671f92d56620fc1d9cdfb2441a1d99802e9e&v=4",
        "https://avatars2.githubusercontent.com/u/2031493?s=460&u=bf29b77796eb28450aa6e7ff97e541ca82701a3e&v=4",
        "https://avatars2.githubusercontent.com/u/3713580?s=460&u=85c56f249bb125f09df09701dfe155cb19950426&v=4",
        "https://avatars2.githubusercontent.com/u/4090245?s=400&u=37f49f0993a7d68886786bc7295eab7760b0227f&v=4"
    )

    private val developerRepository = intArrayOf(
        102,
        37,
        9,
        30,
        56,
        28,
        44,
        110,
        1064,
        65
    )

    private val developerFollowers = intArrayOf(
        56995,
        5153,
        7972,
        14725,
        788,
        18628,
        277,
        178,
        428,
        465
    )

    private val developerFollowing = intArrayOf(
        12,
        2,
        0,
        1,
        0,
        3,
        39,
        23,
        61,
        10
    )

    private val mostPopularName = arrayOf(
        "Developer",
        "Challenges",
        "Study Room"
    )

    private val mostPopularImage = arrayOf(
        "https://static.vecteezy.com/system/resources/previews/000/228/437/non_2x/female-developer-vector-illustration.jpg",
        "https://img.freepik.com/free-vector/man-woman-business-reward-satisfaction-employee_159757-33.jpg?size=626&ext=jpg",
        "https://img.freepik.com/free-vector/students-studying-textbooks_74855-5294.jpg?size=626&ext=jpg"
    )


    val listDataDeveloperDetail : ArrayList<DeveloperDetail>
        get() {
            val listData = arrayListOf<DeveloperDetail>()
            for (position in developerName.indices) {
                val developer = DeveloperDetail(
                    developerName[position],
                    developerUsername[position],
                    developerLocation[position],
                    developerCompany[position],
                    developerAvatar[position],
                    developerRepository[position],
                    developerFollowers[position],
                    developerFollowing[position]
                )
                listData.add(developer)
            }
            return listData
        }

    val takeData = developerFollowers.take(5)
    val listDataMostPopular : ArrayList<DeveloperDetail>
        get() {
            val listDataDeveloper = arrayListOf<DeveloperDetail>()
            for (position in takeData.indices) {
                val developer = DeveloperDetail(
                    developerName[position],
                    developerUsername[position],
                    developerLocation[position],
                    developerCompany[position],
                    developerAvatar[position],
                    developerRepository[position],
                    developerFollowers[position],
                    developerFollowing[position]
                )
                listDataDeveloper.add(developer)
            }
            return listDataDeveloper
        }

    val listDataExploreContent : ArrayList<Content>
        get() {
            val listExploreContent = arrayListOf<Content>()
            for (position in mostPopularName.indices) {
                val content = Content(
                    mostPopularName[position],
                    mostPopularImage[position]
                )
                listExploreContent.add(content)
            }
            return listExploreContent
        }

}