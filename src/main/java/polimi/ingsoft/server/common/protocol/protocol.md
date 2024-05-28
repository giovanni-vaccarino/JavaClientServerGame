## Socket messaging protocol documentation

<code>
SockeMessage {
    type: String,
    payload: Serializable
}
</code>

### Match joining / creation

<p>
    Types:
</p>
<ul>
    <li> MATCHES_LIST_REQUEST</li>
    <li> MATCHES_LIST_UPDATE</li>
    <li> MATCH_JOIN_REQUEST</li>
    <li> MATCH_JOIN_UPDATE</li>
    <li> MATCH_CREATE_REQUEST</li>
    <li> MATCH_CREATE_UPDATE</li>
    <li> LOBBY_INFORMATION_REQUEST</li>
    <li> LOBBY_INFORMATION_UPDATE</li>
    <li> LOBBY_PLAYER_JOINED_UPDATE</li>
    <li> LOBBY_PLAYER_LEFT_UPDATE</li>
</ul>