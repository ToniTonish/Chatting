<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:a="localhost/Chatting/conversation">
	<xsl:template match="/">
		<xsl:for-each select="//a:message">
			<div class="message_container" name="message_container">
			<xsl:attribute name="user">
				<xsl:value-of select="a:user" />
			</xsl:attribute>
				<span class="userChat">
					<xsl:value-of select="a:user" />
				</span>
				<span class="timeChat">
					<xsl:value-of select="a:time" />
				</span>
				<div class="messChat">
					<xsl:value-of select="a:text" />
				</div>
			</div>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
