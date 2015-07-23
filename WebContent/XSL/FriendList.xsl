<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:a="localhost/Chatting/friend_list">
	<xsl:template match="/">
		<div>
			<xsl:for-each select="//a:user">
				<div class="user" name="user">
				<xsl:attribute name="id_user">
					<xsl:value-of select ="a:userId" />
				</xsl:attribute>
				
				<!-- 	<span style="display: none;">
						<xsl:value-of select="a:userId" />
					</span>  -->
					<xsl:value-of select="a:username" />
				<img src="images/remF.png" title="Remove Friend" alt="Remove Friend" onclick="removeFriend(this)"/>
				<img src="images/chat.png" title="Open Chat" alt="Open Chat" onclick="openC(this, 1)" />
				</div>
			</xsl:for-each>
		</div>
	</xsl:template>
</xsl:stylesheet>
