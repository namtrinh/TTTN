import { Component, ElementRef, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import {QRCodeComponent} from 'angularx-qrcode';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  standalone: true,
  imports: [
    QRCodeComponent
  ],
  styleUrls: ['./ticket.component.css']
})
export class TicketComponent {
}
