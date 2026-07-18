$(document).ready(function() {

  // Initialize visibility: if both sections exist show participant and hide loan
  if ("#form1" && "#form2Section") {
    if ($("#form1").length && $("#form2Section").length) {
      $("#form1").show();
      $("#form2Section").hide();
    } else if ($("#form2Section").length) {
      $("#form2Section").show();
      $("#form1").hide();
    } else if ($("#form1").length) {
      $("#form1").show();
    }
  }

  // Start with accordion panels hidden
  $(".panel").hide();

  // Accordion toggle
  $(".accordion").click(function() {
    $(this).next(".panel").slideToggle();
  });

  // Navigation buttons
  $("#nextBtn").click(function() {
    $("#form1").show();
    $("#form2Section").hide();
  });

  $("#backBtn").click(function() {
    $("#form2Section").show();
    $("#form1").hide();
  });

  // ----- Layout: force 3/2/1 column layout via JS (handles templates without .form-group) -----
  function applyColumnLayout() {
    var rows = document.querySelectorAll('.form-row');
    rows.forEach(function(row) {
      var children = Array.prototype.slice.call(row.children).filter(function(el){
        // ignore hidden elements
        return el.offsetParent !== null;
      });
      var containerWidth = row.clientWidth || row.getBoundingClientRect().width;
      var cols = 3;
      if (containerWidth < 600) cols = 1;
      else if (containerWidth < 900) cols = 2;
      var gap = 20; // match CSS gap
      // compute column width in pixels
      var totalGap = (cols - 1) * gap;
      var colWidth = Math.floor((containerWidth - totalGap) / cols);
      // apply width to each child
      children.forEach(function(child, idx) {
        // if child has class full-width, let it span 100%
        if (child.classList.contains('full-width')) {
          child.style.width = '100%';
          child.style.flex = '0 0 100%';
        } else {
          // set to colWidth
          child.style.boxSizing = 'border-box';
          child.style.width = colWidth + 'px';
          child.style.flex = '0 0 ' + colWidth + 'px';
        }
      });
    });
  }

  // debounce helper
  function debounce(fn, delay) {
    var t;
    return function() {
      clearTimeout(t);
      t = setTimeout(fn, delay);
    };
  }

  // run on load and resize
  applyColumnLayout();
  window.addEventListener('resize', debounce(applyColumnLayout, 120));

  // ----- Participant (Form 1) local save -----
  let part1List = JSON.parse(localStorage.getItem("part1List")) || [];

  function renderPart1Links() {
    $("#savedPart1Links").empty();
    part1List.forEach(function(item, index) {
      $("#savedPart1Links").append(
        `<a href="#" class="part1Link" data-id="${index}">${item.id}</a><br>`
      );
    });
  }

  $("#saveBtn").click(function(event) {
    if (event) event.preventDefault();

    part1List = JSON.parse(localStorage.getItem("part1List")) || [];

    let part1 = {
      id: "PARTICIPANT-" + (part1List.length + 1),
      prtcptenttyId: $("#prtcptenttyId").val(),
      fulnm: $("#fulnm").val(),
      partytyp: $("#partytyp").val(),
      cntrprtycntnm: $("#cntrprtycntnm").val(),
      cntrprtycntdsgn: $("#cntrprtycntdsgn").val(),
      cntrprtycntmobno: $("#cntrprtycntmobno").val(),
      altmobno: $("#altmobno").val(),
      emlid: $("#emlid").val(),
      altemlid: $("#altemlid").val(),
      panno: $("#panno").val(),
      signatoryAadhar: $("#signatoryAadhar").val(),
      signatoryGender: $("#signatoryGender").val(),
      cntrprtyaddr: $("#cntrprtyaddr").val(),
      comaddr: $("#comaddr").val(),
      pin: $("#pin").val(),
      regoffpin: $("#regoffpin").val(),
      doi: $("#doi").val(),
      lglcnstn: $("#lglcnstn").val(),
      reltocntrct: $("#reltocntrct").val(),
      ovdtype: $("#ovdtype").val(),
      ovdid: $("#ovdid").val(),
      cin: $("#cin").val(),
      kin: $("#kin").val(),
      seqno: $("#seqno").val(),
      documentIds: $("#documentIds").val()
    };

    part1List.push(part1);
    localStorage.setItem("part1List", JSON.stringify(part1List));
    renderPart1Links();

    // Clear form inputs
    $("#form1").find("input").val("");
    $("#form1").find("select").prop("selectedIndex", 0);

    // reapply layout because inputs cleared (keeps sizes consistent)
    applyColumnLayout();

    alert(part1.id + " Saved");
  });

  // Load saved participant into form
  $("#savedPart1Links").on("click", ".part1Link", function(e) {
    e.preventDefault();
    let index = $(this).data("id");
    let item = part1List[index];
    if (!item) return;
    $("#prtcptenttyId").val(item.prtcptenttyId);
    $("#fulnm").val(item.fulnm);
    $("#partytyp").val(item.partytyp);
    $("#cntrprtycntnm").val(item.cntrprtycntnm);
    $("#cntrprtycntdsgn").val(item.cntrprtycntdsgn);
    $("#cntrprtycntmobno").val(item.cntrprtycntmobno);
    $("#altmobno").val(item.altmobno);
    $("#emlid").val(item.emlid);
    $("#altemlid").val(item.altemlid);
    $("#panno").val(item.panno);
    $("#signatoryAadhar").val(item.signatoryAadhar);
    $("#signatoryGender").val(item.signatoryGender);
    $("#cntrprtyaddr").val(item.cntrprtyaddr);
    $("#comaddr").val(item.comaddr);
    $("#pin").val(item.pin);
    $("#regoffpin").val(item.regoffpin);
    $("#doi").val(item.doi);
    $("#lglcnstn").val(item.lglcnstn);
    $("#reltocntrct").val(item.reltocntrct);
    $("#ovdtype").val(item.ovdtype);
    $("#ovdid").val(item.ovdid);
    $("#cin").val(item.cin);
    $("#kin").val(item.kin);
    $("#seqno").val(item.seqno);
    $("#documentIds").val(item.documentIds);

    // reapply layout after populating
    applyColumnLayout();
  });

  renderPart1Links();

  // ----- Loan (Form 2) local save -----
  $("#saveForm2").click(function() {
    const form2Data = {
      loandtls: {
        loanno: $("#loanno").val(),
        snctnno: $("#snctnno").val(),
        chgamt: $("#chgamt").val(),
        crdtsubtyp: $("#crdtsubtyp").val(),
        currofsanc: $("#currofsanc").val(),
        dtofsnctn: $("#dtofsnctn").val(),
        fcltynm: $("#fcltynm").val(),
        fundtyp: $("#fundtyp").val(),
        isacctclosed: $("#isacctclosed").val(),
        ntrofcrdt: $("#ntrofcrdt").val(),
        emiamt: $("#emiamt").val(),
        rtofint: $("#rtofint").val(),
        amtovrdue: $("#amtovrdue").val(),
        dtofdbrs: $("#dtofdbrs").val(),
        intamt: $("#intamt").val(),
        oldaccno: $("#oldaccno").val(),
        priamt: $("#priamt").val(),
        dpd: $("#dpd").val(),
        dp: $("#dp").val(),
        loc: $("#loc").val(),
        rpyfrq: $("#rpyfrq").val(),
        lndngarrg: $("#lndngarrg").val(),
        snctnamt: $("#snctnamt").val(),
        tenure: $("#tenure").val(),
        toutstndamt: $("#toutstndamt").val()
      },
      scrtydtls: {
        loanno: $("#scrty_loanno").val(),
        dtofvltn: $("#dtofvltn").val(),
        vlofscrty: $("#vlofscrty").val(),
        assetid: $("#assetid").val(),
        scrtyidcersai: $("#scrtyidcersai").val(),
        asstyp: $("#asstyp").val(),
        scrtyidroc: $("#scrtyidroc").val(),
        dscofscrty: $("#dscofscrty").val(),
        typofchrg: $("#typofchrg").val(),
        dtofcrtn: $("#dtofcrtn").val()
      },
      entty_dtls: {
        entty_pan: $("#entty_pan").val(),
        entty_name: $("#entty_name").val(),
        lgl_cnstn: $("#lgl_cnstn").val(),
        doi: $("#doi").val(),
        eml_id: $("#eml_id").val(),
        reg_addr: $("#reg_addr").val(),
        reg_pin: $("#reg_pin").val(),
        comm_addr: $("#comm_addr").val(),
        cntct_no: $("#cntct_no").val(),
        comm_pin: $("#comm_pin").val()
      },
      estampdtls: {
        firstparty: $("#firstparty").val(),
        considerationPrice: $("#considerationPrice").val(),
        stampDutyAmount: $("#stampDutyAmount").val(),
        stampdutyPaidby: $("#stampdutyPaidby").val(),
        secondparty: $("#secondparty").val(),
        documentID: $("#documentID").val(),
        articleCode: $("#articleCode").val(),
        descriptionofDocument: $("#descriptionofDocument").val(),
        firstPartyOVDType: $("#firstPartyOVDType").val(),
        firstPartyOVDValue: $("#firstPartyOVDValue").val(),
        secondPartyOVDType: $("#secondPartyOVDType").val(),
        secondPartyOVDValue: $("#secondPartyOVDValue").val(),
        firstPartyPin: $("#firstPartyPin").val(),
        secondPartyPin: $("#secondPartyPin").val()
      }
    };

    localStorage.setItem("form2Cache", JSON.stringify(form2Data));
    alert("Form 2 Saved Successfully");
  });

  // ----- File selection/display (Form 2) -----
  let selectedFiles = [];

  $("#loanDocument").on("change", function() {
    $.each(this.files, function(_, file) {
      selectedFiles.push(file);
    });
    displayFiles();
    this.value = "";
  });

  function displayFiles() {
    $("#fileList").empty();
    selectedFiles.forEach(function(file, idx) {
      $("#fileList").append(
        `<div>${idx + 1}. ${file.name} (${(file.size / 1024 / 1024).toFixed(2)} MB)</div>`
      );
    });
  }

  // Clear cache button
  $("#clearCache").click(function() {
    localStorage.removeItem("part1List");
    localStorage.removeItem("form2Cache");
    part1List = [];
    selectedFiles = [];
    $("#savedPart1Links").empty();
    $("#fileList").empty();
    $("#form1").find("input, textarea").val("");
    $("#form1").find("select").prop("selectedIndex", 0);
    $("#form2Section").find("input, textarea").val("");
    $("#form2Section").find("select").prop("selectedIndex", 0);
    alert("All saved data cleared");
  });

});
