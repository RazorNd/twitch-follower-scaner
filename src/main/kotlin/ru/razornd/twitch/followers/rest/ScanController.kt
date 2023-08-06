/*
 * Copyright 2023 Daniil <RazorNd> Razorenov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.razornd.twitch.followers.rest

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.*
import ru.razornd.twitch.followers.FollowerScan
import ru.razornd.twitch.followers.service.ScanService

@RestController
@RequestMapping("/api/scans")
class ScanController(private val service: ScanService) {

    @GetMapping
    suspend fun getAll(@AuthenticationPrincipal user: OidcUser): Collection<FollowerScan> =
        service.findScans(user.idToken.subject)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun startScan(@AuthenticationPrincipal user: OidcUser): FollowerScan =
        service.startScan(user.idToken.subject)
}