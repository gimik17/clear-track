<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
	<html>
	<body>
		<h2><xsl:value-of select="vehicle/name"/></h2>
		<xsl:apply-templates>
			<xsl:with-param name="number" select="1" />
			<!-- Abstand der 1. Front zum 1. Lead -->
			<xsl:with-param name="front" select="vehicle/bounds/position[1]/x"/>
			<!-- Absolute x te Trackerposition zur 1. Front. Wobei x > 1 gilt -->
			<xsl:with-param name="trackerPosition" select="0" />
		</xsl:apply-templates>
	</body>
	</html>
</xsl:template>

<xsl:template match="turnangle">
	<tr>
		<td>Einschlag:</td>
		<td>
			<xsl:value-of select="."/>
			<xsl:value-of select="concat(' ', 'Grad')"/>
		</td>
	</tr>
</xsl:template>

<xsl:template match="bounds">
	<xsl:param name="trackerPosition" />
	
	<xsl:variable name="x1"><xsl:value-of select="position[1]/x"/></xsl:variable>
	<xsl:variable name="x2"><xsl:value-of select="position[2]/x"/></xsl:variable>
	
	<tr>
		<td>Länge:</td>
		<td>
			<xsl:value-of select="concat($x1 - $x2, ' ', 'mm')"/>
		</td>
	</tr>
	<tr>
		<td>Breite:</td>
		<td>
			<xsl:variable name="y1"><xsl:value-of select="position[1]/y"/></xsl:variable>
			<xsl:variable name="y2"><xsl:value-of select="position[3]/y"/></xsl:variable>
			<xsl:value-of select="concat($y1 - $y2, ' ', 'mm')"/>
		</td>
	</tr>
	<xsl:if test="$trackerPosition > 0">
	<tr>
		<td>Gesamtlänge:</td>
		<td>
			<xsl:value-of select="concat($trackerPosition - $x2, ' ', 'mm')"/>
		</td>
	</tr>
	</xsl:if>
</xsl:template>

<xsl:template match="trailer">
	<xsl:param name="trackerPosition" />
	<tr>
		<td>Anhänger:</td>
		<td>
			<xsl:variable name="trailerPosition"><xsl:value-of select="position[1]/x"/></xsl:variable>
			<xsl:value-of select="concat(0 - $trailerPosition, ' ', 'mm')"/>
		</td>
	</tr>
</xsl:template>

<xsl:template match="vehicle">
	<xsl:param name="number" />
	<xsl:param name="trackerPosition" />
	<xsl:param name="front" />
	<xsl:if test="$number > 1">
		<h4><xsl:value-of select="name"/></h4>
	</xsl:if>
	<table>
		<xsl:apply-templates select="turnangle"/>
		<xsl:variable name="lead"><xsl:value-of select="tracker/lead/position/x"/></xsl:variable>
		<xsl:variable name="trail"><xsl:value-of select="tracker/trail/position/x"/></xsl:variable>
		<tr>
			<td>Radstand:</td>
			<td>
				<xsl:value-of select="concat($lead - $trail, ' ', 'mm')"/>
			</td>
		</tr>
		<xsl:apply-templates select="bounds">
			<xsl:with-param name="trackerPosition" select="$trackerPosition" />
		</xsl:apply-templates>
		<xsl:apply-templates select="trailer"/>
	</table>
	
	<xsl:variable name="trailerFile"><xsl:value-of select="trailer/file"/></xsl:variable>
	<xsl:variable name="trailerPosition"><xsl:value-of select="trailer/position[1]/x"/></xsl:variable>
	
	<xsl:if test="string-length($trailerFile) > 0">
		<xsl:if test="not(starts-with($trailerFile, 'trailers'))">
			<xsl:apply-templates select="document(concat('trailers/', $trailerFile))/vehicle">
				<xsl:with-param name="number" select="$number + 1" />
				<xsl:with-param name="trackerPosition" select="$front + $trackerPosition - $trailerPosition" />
				<xsl:with-param name="front" select="0"/>
			</xsl:apply-templates>
		</xsl:if>
		
		<xsl:if test="starts-with($trailerFile, 'trailers')">
			<xsl:apply-templates select="document($trailerFile)/vehicle">
				<xsl:with-param name="number" select="$number + 1" />
				<xsl:with-param name="trackerPosition" select="$front + $trackerPosition - $trailerPosition" />
				<xsl:with-param name="front" select="0"/>
			</xsl:apply-templates>
		</xsl:if>
	</xsl:if>
	
</xsl:template>

</xsl:stylesheet>