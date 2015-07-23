<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:a="localhost/Chatting/search_friends">
	<xsl:template match="/">
		<div>
			<xsl:for-each select="//a:user">
				<div>
					<xsl:attribute name="id_user">
						<xsl:value-of select="a:userId" />
					</xsl:attribute>
					<xsl:value-of select="a:username" />
					<img src="images/addF.png" title="Add Friend" alt="Add Friend"
					onclick="addFriend(this)" style="cursor: pointer;" />
					<img src="images/chat.png" title="Open Chat" alt="Open Chat"
						onclick="openChat(this, 1)" style="cursor: pointer;" />

				</div>

			</xsl:for-each>
		</div>
	</xsl:template>
</xsl:stylesheet>
