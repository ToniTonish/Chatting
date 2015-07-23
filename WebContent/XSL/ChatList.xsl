<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:a="localhost/Chatting/chat_list">
	<xsl:template match="/">
		<xsl:for-each select="//a:chat">
			<div class="user_chatlist" onclick="openC(this,0)">
				<xsl:attribute name="id_user">
					<xsl:value-of select="a:userId" />
				</xsl:attribute>
				<div class="chatlist_name">
					<xsl:value-of select="a:username" />
				</div>
				<div class="chatlist_time">
					<xsl:value-of select="a:time" />
				</div>
				<img src="images/notifyOff.png" alt="No new message" title="No new message"/>
			</div>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
