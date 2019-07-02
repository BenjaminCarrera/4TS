/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { BaseTarifaDeleteDialogComponent } from 'app/entities/base-tarifa/base-tarifa-delete-dialog.component';
import { BaseTarifaService } from 'app/entities/base-tarifa/base-tarifa.service';

describe('Component Tests', () => {
  describe('BaseTarifa Management Delete Component', () => {
    let comp: BaseTarifaDeleteDialogComponent;
    let fixture: ComponentFixture<BaseTarifaDeleteDialogComponent>;
    let service: BaseTarifaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [BaseTarifaDeleteDialogComponent]
      })
        .overrideTemplate(BaseTarifaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BaseTarifaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BaseTarifaService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
