/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstatusReqCanDetailComponent } from 'app/entities/estatus-req-can/estatus-req-can-detail.component';
import { EstatusReqCan } from 'app/shared/model/estatus-req-can.model';

describe('Component Tests', () => {
  describe('EstatusReqCan Management Detail Component', () => {
    let comp: EstatusReqCanDetailComponent;
    let fixture: ComponentFixture<EstatusReqCanDetailComponent>;
    const route = ({ data: of({ estatusReqCan: new EstatusReqCan(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusReqCanDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstatusReqCanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstatusReqCanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estatusReqCan).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
