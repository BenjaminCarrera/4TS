/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { PrioridadReqDetailComponent } from 'app/entities/prioridad-req/prioridad-req-detail.component';
import { PrioridadReq } from 'app/shared/model/prioridad-req.model';

describe('Component Tests', () => {
  describe('PrioridadReq Management Detail Component', () => {
    let comp: PrioridadReqDetailComponent;
    let fixture: ComponentFixture<PrioridadReqDetailComponent>;
    const route = ({ data: of({ prioridadReq: new PrioridadReq(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [PrioridadReqDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PrioridadReqDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrioridadReqDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.prioridadReq).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
