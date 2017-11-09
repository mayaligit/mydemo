<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
          xmlns:o="urn:schemas-microsoft-com:office:office"
          xmlns:x="urn:schemas-microsoft-com:office:excel"
          xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
          xmlns:html="http://www.w3.org/TR/REC-html40">
    <DocumentProperties >
        <Created>2015-06-05T18:19:34Z</Created>
        <LastSaved>2017-10-31T07:18:39Z</LastSaved>
        <Version>16.00</Version>
    </DocumentProperties>
    <OfficeDocumentSettings >
        <AllowPNG/>
        <RemovePersonalInformation/>
    </OfficeDocumentSettings>
    <ExcelWorkbook >
        <WindowHeight>12648</WindowHeight>
        <WindowWidth>22260</WindowWidth>
        <WindowTopX>0</WindowTopX>
        <WindowTopY>0</WindowTopY>
        <ProtectStructure>False</ProtectStructure>
        <ProtectWindows>False</ProtectWindows>
    </ExcelWorkbook>
    <Styles>
        <Style ss:ID="Default" ss:Name="Normal">
            <Alignment ss:Vertical="Bottom"/>
            <Borders/>
            <Font ss:FontName="等线" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
            <Interior/>
            <NumberFormat/>
            <Protection/>
        </Style>
        <Style ss:ID="s17">
            <Borders>
            <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
            <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
            <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
            <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
            </Borders>
        </Style>
        <Style ss:ID="s68">
            <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
        </Style>
        <Style ss:ID="s70">
            <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
            <Borders>
             <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
            <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
            <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
            <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
            </Borders>
              <Font ss:FontName="等线" x:CharSet="134" ss:Size="20" ss:Color="#000000"
            ss:Bold="1"/>
        </Style>
        <Style ss:ID="s72">
            <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
            <Borders>
             <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
            <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
            <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
            <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
            </Borders>
              <Font ss:FontName="等线" x:CharSet="134" ss:Size="11" ss:Color="#000000"
            ss:Bold="1"/>
        </Style>
    </Styles>
    <Worksheet ss:Name="Sheet1">
        <Table ss:ExpandedColumnCount="9" ss:ExpandedRowCount="${attendanceList?size + 3}" x:FullColumns="1"
               x:FullRows="1" ss:DefaultRowHeight="13.8">
            <Column ss:Index="2" ss:AutoFitWidth="0" ss:Width="31.200000000000003"/>
            <Column ss:AutoFitWidth="0" ss:Width="85.2"/>
            <Column ss:AutoFitWidth="0" ss:Width="139.19999999999999"/>
            <Column ss:AutoFitWidth="0" ss:Width="166.2"/>
            <Column ss:AutoFitWidth="0" ss:Width="139.19999999999999"/>
            <Column ss:AutoFitWidth="0" ss:Width="166.2"/>
            <Column ss:AutoFitWidth="0" ss:Width="85.2" ss:Span="1"/>
            <Row ss:AutoFitHeight="0" ss:Height="30" ss:StyleID="s68">
                <Cell ss:Index="2" ss:MergeAcross="7" ss:StyleID="s70"><Data ss:Type="String">${currMonth} 考勤打卡</Data></Cell>
            </Row>
            <Row ss:AutoFitHeight="0" ss:Height="22.05" ss:StyleID="s68">
                <Cell ss:Index="2" ss:StyleID="s72"><Data ss:Type="String">编号</Data></Cell>
                <Cell ss:StyleID="s72"><Data ss:Type="String">用户名</Data></Cell>
                <Cell ss:StyleID="s72"><Data ss:Type="String">上班打卡时间</Data></Cell>
                <Cell ss:StyleID="s72"><Data ss:Type="String">上班打卡地址</Data></Cell>
                <Cell ss:StyleID="s72"><Data ss:Type="String">下班打卡时间</Data></Cell>
                <Cell ss:StyleID="s72"><Data ss:Type="String">下班打卡地址</Data></Cell>
                <Cell ss:StyleID="s72"><Data ss:Type="String">考勤状态</Data></Cell>
                <Cell ss:StyleID="s72"><Data ss:Type="String">日报状态</Data></Cell>
            </Row>

        <#list attendanceList as attendance>
            <Row ss:AutoFitHeight="0" ss:Height="19.950000000000003">
                <Cell ss:Index="2" ss:StyleID="s17"><Data ss:Type="String">${attendance_index + 1}</Data></Cell>
                <Cell ss:StyleID="s17"><Data ss:Type="String">${attendance.attendanceUser!}</Data></Cell>
                <Cell ss:StyleID="s17"><Data ss:Type="String"><#if attendance.startTime ??>${attendance.startTime?string('yyyy年MM月dd日 HH:mm:ss')}</#if></Data></Cell>
                <Cell ss:StyleID="s17"><Data ss:Type="String">${attendance.startLocation!}</Data></Cell>
                <Cell ss:StyleID="s17"><Data ss:Type="String"><#if attendance.endTime ??>${attendance.endTime?string('yyyy年MM月dd日 HH:mm:ss')}</#if></Data></Cell>
                <Cell ss:StyleID="s17"><Data ss:Type="String">${attendance.endLocation!}</Data></Cell>
                <Cell ss:StyleID="s17"><Data ss:Type="String"><#if attendance.attendanceStatus == "0">正常<#elseif attendance.attendanceStatus == "1">异常<#elseif attendance.attendanceStatus == "2">外勤</#if></Data></Cell>
                <Cell ss:StyleID="s17"><Data ss:Type="String"><#if attendance.dailyStatus == 0>未完成<#elseif attendance.dailyStatus == 1>已完成</#if></Data></Cell>
            </Row>
        </#list>

        </Table>
        <WorksheetOptions >
            <PageSetup>
                <Header x:Margin="0.3"/>
                <Footer x:Margin="0.3"/>
                <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
            </PageSetup>
            <Selected/>
            <Panes>
                <Pane>
                    <Number>3</Number>
                    <ActiveRow>13</ActiveRow>
                    <ActiveCol>4</ActiveCol>
                </Pane>
            </Panes>
            <ProtectObjects>False</ProtectObjects>
            <ProtectScenarios>False</ProtectScenarios>
        </WorksheetOptions>
    </Worksheet>
</Workbook>
