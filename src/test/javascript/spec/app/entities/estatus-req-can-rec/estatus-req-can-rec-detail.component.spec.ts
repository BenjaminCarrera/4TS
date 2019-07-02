/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstatusReqCanRecDetailComponent } from 'app/entities/estatus-req-can-rec/estatus-req-can-rec-detail.component';
import { EstatusReqCanRec } from 'app/shared/model/estatus-req-can-rec.model';

describe('Component Tests', () => {
  describe('EstatusReqCanRec Management Detail Component', () => {
    let comp: EstatusReqCanRecDetailComponent;
    let fixture: ComponentFixture<EstatusReqCanRecDetailComponent>;
    const route = ({ data: of({ estatusReqCanRec: new EstatusReqCanRec(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusReqCanRecDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstatusReqCanRecDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstatusReqCanRecDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estatusReqCanRec).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
