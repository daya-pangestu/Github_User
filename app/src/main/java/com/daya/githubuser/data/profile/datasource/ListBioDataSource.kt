package com.daya.githubuser.data.profile.datasource

import com.daya.githubuser.data.profile.general.GeneralBio


interface ListBioDataSource {
   suspend fun getListBio(queryName : String = "") : List<GeneralBio>
}

